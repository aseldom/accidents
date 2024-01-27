package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentRepository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class SimpleAccidentService implements AccidentService {

    private final AccidentRepository accidentRepository;
    private final SimpleRuleService simpleRuleService;
    private final SimpleAccidentTypeService simpleAccidentTypeService;

    @Override
    public Accident add(Accident accident) {
        return accidentRepository.add(accident);
    }

    @Override
    public boolean update(Accident accident, Set<Integer> rIds) {
        accident.setType(simpleAccidentTypeService.findById(accident.getType().getId()).get());
        accident.setRules(new HashSet<>(simpleRuleService.findAllByIds(rIds))
        );
        return accidentRepository.update(accident) || add(accident) != null;
    }

    @Override
    public Optional<Accident> findById(int id) {
        return accidentRepository.findById(id);
    }

    @Override
    public Collection<Accident> findAll() {
        return accidentRepository.findAll();
    }

}
