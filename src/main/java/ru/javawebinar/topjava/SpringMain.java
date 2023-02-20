package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management (ARM)
//        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
//            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
//            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
//            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));
//        }
        try (ConfigurableApplicationContext appCpx = new ClassPathXmlApplicationContext("spring/spring-app.xml")){
            System.out.println("Bean definition names: " + Arrays.toString(appCpx.getBeanDefinitionNames()));
            MealRestController mealRestController = appCpx.getBean(MealRestController.class);
            mealRestController.create(new Meal(LocalDateTime.of(2023, Month.JANUARY, 30, 10, 0), "Завтрак", 1000));
        }
//        User user = new User(null, "userName", "email@mail.ru", "password", Role.ADMIN);
//        User user1 = new User(null, "aserName", "email@mail.ru", "password", Role.ADMIN);
//        User user2 = new User(null, "userName", "amail@mail.ru", "password", Role.ADMIN);
//        UserRepository repository = new InMemoryUserRepository();
//        repository.save(user);
//        repository.save(user1);
//        repository.save(user2);
//        List <User> list = repository.getAll();
//
//        list.forEach(System.out::println);

        }
    }

