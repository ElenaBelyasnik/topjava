package ru.javawebinar.topjava.data;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface MealCrud {

    Meal save(Meal meal);

    Meal read(int id);

    boolean delete(int id);

    Collection<Meal> getAll();
}