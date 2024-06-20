package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.data.InMemMealStorage;
import ru.javawebinar.topjava.data.MealCrud;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

import static javax.xml.datatype.DatatypeConstants.MINUTES;
import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealCrud storage;

    @Override
    public void init() {
        storage = new InMemMealStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("doGet: create or update or delete or all");
        String action = request.getParameter("action");
        action = action == null ? "all" : action;
        String strId = request.getParameter("id");

        switch (action) {
            case "create":
            case "update":
                log.info(action);
                Meal meal = strId == null ? new Meal(null,
                        LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
                        , "", 500) :
                        storage.read(Integer.parseInt(strId));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/meal.jsp").forward(request, response);
                break;
            case "delete":
                log.info("Delete id = {}", strId);
                if (!(strId == null)) {
                    boolean result = storage.delete(Integer.parseInt(strId));
                    log.info("Delete = " + result);
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
        Meal meal = storage.save(new Meal(strId.isEmpty() ? null : Integer.parseInt(strId),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories"))));
        response.sendRedirect("meals");
    }
}