package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.MemoryAccidentTypeRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleAccidentTypeService implements AccidentTypeService {

    private MemoryAccidentTypeRepository memoryAccidentTypeRepository;

    @Override
    public AccidentType add(AccidentType accidentType) {
        return memoryAccidentTypeRepository.add(accidentType);
    }

    @Override
    public boolean update(AccidentType accidentType) {
        return memoryAccidentTypeRepository.update(accidentType);
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        return memoryAccidentTypeRepository.findById(id);
    }

    @Override
    public Collection<AccidentType> findAll() {
        return memoryAccidentTypeRepository.findAll();
    }

}
