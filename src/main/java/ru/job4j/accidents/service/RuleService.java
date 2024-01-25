package ru.job4j.accidents.service;

import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.Optional;

public interface RuleService {

    Rule add(Rule rule);

    boolean update(Rule rule);

    Optional<Rule> findById(int id);

    Collection<Rule> findAll();

}
