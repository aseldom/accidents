package ru.job4j.accidents.service;

import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface AccidentService {

    Optional<Accident> add(Accident accident);

    boolean update(Accident accident, Set<Integer> rIds);

    Optional<Accident> findById(int id);

    Collection<Accident> findAll();

}
