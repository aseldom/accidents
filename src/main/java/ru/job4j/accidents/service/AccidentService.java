package ru.job4j.accidents.service;

import ru.job4j.accidents.model.Accident;

import java.util.Collection;

public interface AccidentService {

    Accident add(Accident accident);

    Collection<Accident> findAll();

}
