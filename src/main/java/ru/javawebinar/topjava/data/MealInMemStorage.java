package ru.javawebinar.topjava.data;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class MealInMemStorage implements CrudMeal {
    private static final Logger log = getLogger(MealInMemStorage.class);
    private final Map<Integer, Meal> storage = new ConcurrentHashMap<>();
    private final AtomicInteger mealCounter = new AtomicInteger(0);

    public MealInMemStorage() {
        MealsUtil.meals.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        Integer id = meal.getId();

        if (id == null) {
            meal.setId(mealCounter.incrementAndGet());
            storage.put(meal.getId(), meal);
            return meal;
        } else if (storage.get(id) != null) {
            storage.put(meal.getId(), meal);
            return meal;
        }
        return null;
    }

    @Override
    public Meal read(int id) {
        return storage.get(id);
    }

    @Override
    public boolean delete(int id) {
        return storage.remove(id) != null;
    }

    @Override
    public Collection<Meal> getAll() {
        return storage.values();
    }
}
