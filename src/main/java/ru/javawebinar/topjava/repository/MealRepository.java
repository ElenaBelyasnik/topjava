package ru.javawebinar.topjava.repository;

import org.springframework.stereotype.Component;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


public interface MealRepository {
    // null if updated meal does not belong to userId
    Meal save(Meal meal, int userId);

    // false if meal does not belong to userId
    boolean delete(int id, int userId);

    // null if meal does not belong to userId
    Meal get(int id, int userId);

    // ORDERED dateTime desc
    List<Meal> getAll(int userId);

    List<Meal> isBetweenDates(LocalDateTime startDate, LocalDateTime endDate, int userId);
}
