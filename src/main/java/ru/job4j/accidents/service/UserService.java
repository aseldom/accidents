package ru.job4j.accidents.service;

import ru.job4j.accidents.model.User;

import java.util.Optional;

public interface UserService {

    Optional<User> add(User user);

    Optional<User> findById(int userId);

    Optional<User> findByLogin(String login);

}