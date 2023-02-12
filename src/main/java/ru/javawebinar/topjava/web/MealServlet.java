package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.*;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private static final Logger log = getLogger(MealServlet.class);

    public final int CALORIESPERDAY = 2000;

    List<Meal> meals = new ArrayList<>(Arrays.asList(
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
    ));


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        log.debug("redirect to meals");
        try {
            if (req.getParameter("action").equalsIgnoreCase("insert")) {
                req.getRequestDispatcher("/addMeal.jsp").include(req, resp);
            }
        }catch (NullPointerException e) {
            List<MealTo> mealTo = MealsUtil.withoutFilteredByStreams(meals, CALORIESPERDAY);
            req.setAttribute("mealTo", mealTo);
            req.getRequestDispatcher("/meals.jsp").forward(req, resp);
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        log.debug("redirect to addMeal");
        Meal meal = new Meal();
        LocalDateTime dateTime = null;
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.US).parse(req.getParameter("trip-start"));
            dateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        } catch (ParseException e) {
            System.out.println("баста");
            e.printStackTrace();
        }
        meal.setDateTime(dateTime);
        req.setCharacterEncoding("UTF-8");
        String description = req.getParameter("description");
        meal.setDescription(description);
        meal.setCalories(Integer.parseInt(req.getParameter("calories")));
        meals.add(meal);
        RequestDispatcher view = req.getRequestDispatcher("/meals.jsp");
        List<MealTo> mealTo = MealsUtil.withoutFilteredByStreams(meals, CALORIESPERDAY);
        req.setAttribute("mealTo", mealTo);
        view.forward(req, resp);
    }
}