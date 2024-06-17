package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.data.Storage;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private Storage storage;

    @Override
    public void init() {
        storage = new Storage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forvard to meals");
        String action = request.getParameter("action");
        log.info("action = " + action);

        if ("create".equals(action)) {
            log.info(action);
            Meal meal = new Meal(LocalDateTime.now(),
                    "",
                    500);
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("/meal.jsp").forward(request, response);
        }

        if ("update".equals(action)) {
            log.info(action);
                int id = Integer.parseInt(request.getParameter("id"));
                Meal meal = storage.read(id);
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("/meal.jsp").forward(request, response);
        }

        if ("delete".equals(action)) {
            log.info(action);
            String strId = request.getParameter("id");
            log.info("Delete id = {}", strId);
            if (!strId.equals("")) {
                storage.delete(Integer.parseInt(strId));
            }
            response.sendRedirect("meals");
        }

        if (action == null) {
            request.setAttribute("meals", MealsUtil.getListMealTo(storage.getAll(), MealsUtil.CALORIES_PER_DAY));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
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
        storage.merge(meal);
        response.sendRedirect("meals");
    }
}