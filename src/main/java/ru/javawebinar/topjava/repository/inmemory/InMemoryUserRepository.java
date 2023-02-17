package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private final Map<Integer, User> userRepository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return userRepository.remove(id) != null;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            userRepository.put(user.getId(), user);
            return user;
        }
        return userRepository.computeIfPresent(user.getId(), (id,oldUser) -> user);
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return userRepository.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        class UserNameComparator implements Comparator<User> {
            @Override
            public int compare(User o1, User o2) {
                return o1.getName().compareTo(o2.getName());
            }
        }
        class UserEmailComparator implements Comparator<User> {
            @Override
            public int compare(User o1, User o2) {
                return o1.getEmail().compareTo(o2.getEmail());
            }
        }
        Comparator<User> comparator = new UserNameComparator()
                .thenComparing(new UserEmailComparator());
        List<User> users = new ArrayList<>(userRepository.values());
        users.sort(comparator);

        return users;
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
       return userRepository.values().stream().
               filter(user -> user.getEmail().equals(email)).findFirst().orElse(null);

    }
}
