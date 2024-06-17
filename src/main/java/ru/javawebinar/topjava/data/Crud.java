package ru.javawebinar.topjava.data;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

public interface Crud {
    Meal merge(Meal meal);

    //void update(Meal meal);

    Meal read(int id);

    boolean delete(int id);

    Collection<Meal> getAll();
}
