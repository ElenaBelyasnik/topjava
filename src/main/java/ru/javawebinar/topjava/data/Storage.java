package ru.javawebinar.topjava.data;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.MealServlet;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class Storage implements Crud{
    private static final Logger log = getLogger(MealServlet.class);
    private final Map<Integer, Meal> storage = new ConcurrentHashMap<>();
    private final AtomicInteger mealCounter = new AtomicInteger(0);

    public Storage() {
        MealsUtil.meals.forEach(this::merge);
    }

    @Override
    public Meal merge(Meal meal) {
        log.info("merge");
        Integer id = meal.getId();
        if (id == null) {
            meal.setId(mealCounter.incrementAndGet());
        }
        storage.put(meal.getId(), meal);
        return meal;
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
