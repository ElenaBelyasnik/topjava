package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
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
        save(new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 9, 0), "Админский ланч", 510), ADMIN_ID);
        save(new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 10, 0), "Админский ужин", 1500), ADMIN_ID);

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
        Map<Integer, Meal> meals = getUserMeals(userId);
        return meals != null && meals.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> meals = getUserMeals(userId);
        return meals != null ? meals.get(id) : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getFilteredMealList(userId, meal -> true);
    }

    @Override
    public List<Meal> isBetweenDates(LocalDateTime mealStartDateTime, LocalDateTime mealEndDateTime, int userId) {
        return getFilteredMealList(userId, meal -> DateTimeUtil.isBetweenDates(
                meal.getDate(),
                mealStartDateTime.toLocalDate(),
                mealEndDateTime.toLocalDate()));
    }

    public List<Meal> getFilteredMealList(int userId, Predicate<Meal> filter) {
        Map<Integer, Meal> meals = getUserMeals(userId);
            return meals.isEmpty() ? Collections.emptyList() :
                    meals.values().stream()
                            .filter(filter)
                            //.sorted(Comparator.comparing(Meal::getDateTime).reversed())
                            .collect(Collectors.toList());
    }

    private Map<Integer, Meal> getUserMeals(int userId) {
        return userMealRepo.get(userId);
    }
}

