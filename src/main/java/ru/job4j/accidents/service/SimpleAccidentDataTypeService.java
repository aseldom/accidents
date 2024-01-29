package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentDataTypeRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@Primary
@AllArgsConstructor
public class SimpleAccidentDataTypeService implements AccidentTypeService {

    private final AccidentDataTypeRepository accidentDataTypeRepository;

    @Override
    public Optional<AccidentType> findById(int id) {
        return accidentDataTypeRepository.findById(id);
    }

    @Override
    public Collection<AccidentType> findAll() {
        Collection<AccidentType> collection = new ArrayList<>();
        accidentDataTypeRepository.findAll().forEach(collection::add);
        return collection;
    }

}
