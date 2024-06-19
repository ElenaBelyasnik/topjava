package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.data.MealInMemStorage;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealInMemStorage storage;

    @Override
    public void init() {
        storage = new MealInMemStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forvard to meals");
        String action = request.getParameter("action");
        log.info("action = " + action);

        if (action == null) {
            action = "all";
        }

        switch (action) {
            case "create":
                log.info(action);
                Meal mealCreate = new Meal(LocalDateTime.now(), "", 500);
                request.setAttribute("meal", mealCreate);
                request.getRequestDispatcher("/meal.jsp").forward(request, response);
                break;

            case "update":
                log.info(action);
                int id = Integer.parseInt(request.getParameter("id"));
                Meal mealUpdate = storage.read(id);
                request.setAttribute("meal", mealUpdate);
                request.getRequestDispatcher("/meal.jsp").forward(request, response);
                break;

            case "delete":
                log.info(action);
                String strId = request.getParameter("id");
                log.info("Delete id = {}", strId);
                if (!strId.isEmpty()) {
                    storage.delete(Integer.parseInt(strId));
                }
                response.sendRedirect("meals");
                break;
            case "all":
            default:
                request.setAttribute("meals", MealsUtil.getListMealTo(storage.getAll(), MealsUtil.CALORIES_PER_DAY));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("doPost");
        String strId = request.getParameter("id");
        log.info("strId = " + strId);
        Meal meal;
        if ("".equals(strId)) {
            meal = new Meal(LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories")));
        } else {
            meal = new Meal(Integer.parseInt(strId),
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories")));

        }
        log.info(meal.getId() == null ? "Create {}" : "Update {}", meal);
        storage.save(meal);
        response.sendRedirect("meals");
    }
}