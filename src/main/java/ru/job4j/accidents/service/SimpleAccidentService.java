package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentMemRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleAccidentService implements AccidentService {

    private AccidentMemRepository accidentMemRepository;

    @Override
    public Accident add(Accident accident) {
        return accidentMemRepository.add(accident);
    }

    @Override
    public boolean update(Accident accident) {
        return accidentMemRepository.update(accident);
    }

    @Override
    public Optional<Accident> findById(int id) {
        return accidentMemRepository.findById(id);
    }

    @Override
    public Collection<Accident> findAll() {
        return accidentMemRepository.findAll();
    }

}
