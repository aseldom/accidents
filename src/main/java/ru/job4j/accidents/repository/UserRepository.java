package ru.job4j.accidents.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.service.SimpleUserService;

import java.util.Optional;
import java.util.logging.Logger;

public interface UserRepository extends CrudRepository<User, Integer> {

    Logger LOGGER = Logger.getLogger(SimpleUserService.class.getName());

    default Optional<User> add(User user) {
        try {
            return Optional.of(save(user));
        } catch (Exception e) {
            LOGGER.info("Save operation User: " + e);
        }
        return Optional.empty();
    }
}