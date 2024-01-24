package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.MemoryAccidentRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleAccidentService implements AccidentService {

    private MemoryAccidentRepository memoryAccidentRepository;

    @Override
    public Accident add(Accident accident) {
        return memoryAccidentRepository.add(accident);
    }

    @Override
    public boolean update(Accident accident) {
        return memoryAccidentRepository.update(accident);
    }

    @Override
    public Optional<Accident> findById(int id) {
        return memoryAccidentRepository.findById(id);
    }

    @Override
    public Collection<Accident> findAll() {
        return memoryAccidentRepository.findAll();
    }

}
