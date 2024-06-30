package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Component;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository.ADMIN_ID;

@Component
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> userMealRepo = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, InMemoryUserRepository.USER_ID));
        save(new Meal(LocalDateTime.of(2024, Month.JUNE, 1, 14, 0), "Админский ланч", 510), ADMIN_ID);
        save(new Meal(LocalDateTime.of(2024, Month.JUNE, 1, 21, 0), "Админский ужин", 1500), ADMIN_ID);

    }

    @Override
    public Meal save(Meal meal, int userId) {
        Map<Integer, Meal> meals = userMealRepo.computeIfAbsent(userId, usId -> new ConcurrentHashMap<>());
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meals.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return meals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, Meal> meals = getUserMeal(userId);
        try {
            Meal deletedMeal = meals.remove(id);
            return deletedMeal != null;
        } catch (NullPointerException e) {
            return false;
        }
        //return !meals.isEmpty() && meals.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> meals = getUserMeal(userId);
        try {
            return meals.get(id);
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getFilteredMealList(userId, meal -> true);
    }

    @Override
    public List<Meal> isBetweenDates(LocalDateTime mealStartTime, LocalDateTime mealEndTime, int userId) {
        return getFilteredMealList(userId, meal -> DateTimeUtil.isBetweenHalfOpen(
                meal.getDateTime().toLocalTime(),
                mealStartTime.toLocalTime(),
                mealEndTime.toLocalTime()));
    }

    public List<Meal> getFilteredMealList(int userId, Predicate<Meal> filter) {
        try {
            Map<Integer, Meal> meals = getUserMeal(userId);
            return meals.isEmpty() ? Collections.emptyList() :
                    meals.values().stream()
                            .filter(filter)
                            .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                            .collect(Collectors.toList());
        } catch (NullPointerException e) {
            return Collections.emptyList();
        }
    }

    private Map<Integer, Meal> getUserMeal(int userId) {
        return userMealRepo.get(userId);
    }
}

