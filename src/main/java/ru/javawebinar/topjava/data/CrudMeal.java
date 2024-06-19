package ru.javawebinar.topjava.data;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface CrudMeal {
    Map<Integer, Meal> storage = new ConcurrentHashMap<>();

    Meal save(Meal meal);

    Meal read(int id);

    boolean delete(int id);

    Collection<Meal> getAll();
}