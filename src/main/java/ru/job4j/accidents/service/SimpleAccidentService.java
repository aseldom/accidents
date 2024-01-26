package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.AccidentRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
        try {
            Set<Rule> rules = rIds.stream()
                    .map(x -> simpleRuleService.findById(x).get())
                    .collect(Collectors.toSet());
            Optional<AccidentType> accidentTypeOptional = simpleAccidentTypeService.findById(accident.getType().getId());
            accident.setType(accidentTypeOptional.get());
            accident.setRules(rules);
            return accidentRepository.update(accident) || add(accident) != null;
        } catch (Exception e) {
            return false;
        }
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
