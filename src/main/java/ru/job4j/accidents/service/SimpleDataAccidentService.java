package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentDataRepository;

import java.util.*;

@Service
@Primary
@AllArgsConstructor
public class SimpleDataAccidentService implements AccidentService {

    private final AccidentDataRepository accidentDataRepository;
    private final RuleService ruleService;
    private final AccidentTypeService accidentTypeService;

    @Override
    public Optional<Accident> add(Accident accident) {
        return Optional.of(accidentDataRepository.save(accident));
    }

    @Override
    public boolean update(Accident accident, Set<Integer> rIds) {
        try {
            accident.setType(accidentTypeService.findById(accident.getType().getId()).get());
            accident.setRules(new HashSet<>(ruleService.findAllByIds(rIds)));
            return add(accident).isPresent();
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Optional<Accident> findById(int id) {
        return accidentDataRepository.findById(id);
    }

    @Override
    public Collection<Accident> findAll() {
        return accidentDataRepository.findAll();
    }

}
