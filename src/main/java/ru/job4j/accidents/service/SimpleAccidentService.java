package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentMemRepository;

import java.util.Collection;

@Service
@AllArgsConstructor
public class SimpleAccidentService implements AccidentService {

    private AccidentMemRepository accidentMemRepository;

    @Override
    public Accident add(Accident accident) {
        return accidentMemRepository.add(accident);
    }

    @Override
    public Collection<Accident> findAll() {
        return accidentMemRepository.findAll();
    }

}
