package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);
    private User user;

    {
        int id = SecurityUtil.authUserId();
        MealsUtil.meals.forEach(meal -> save(meal,id));
    }

    @Override
    public Meal save(Meal meal, int currentUser) {
        log.info("save {}", meal);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        if(meal.getUserId() != currentUser){
            return null;
        }
        // handle case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int currentUser) {
        log.info("delete {}", id);
        if(repository.get(currentUser).getUserId() != currentUser){
            return false;
        }
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int currentUser) {
        log.info("get {}", id);
        if(repository.get(currentUser).getUserId() != currentUser){
            return null;
        }
        return repository.get(id);
    }

    @Override
    public Collection<Meal> getAll(int currentUser) {
        log.info("getAll");
        if(repository.get(currentUser).getUserId() != currentUser){
            return null;
        }
        class DateComparator implements Comparator<Meal> {

            @Override
            public int compare(Meal o1, Meal o2) {
                return o2.getDateTime().compareTo(o1.getDateTime());
            }
        }
        Comparator<Meal> comparator = new DateComparator();
        return repository.values().stream().sorted(comparator).collect(Collectors.toList());
    }
}

