package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.Set;

public interface RuleRepository {

    Collection<Rule> findAllByIds(Set<Integer> ids);

    Collection<Rule> findAll();

}
