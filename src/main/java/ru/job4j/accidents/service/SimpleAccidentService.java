package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.MemoryAccidentRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SimpleAccidentService implements AccidentService {

    private final MemoryAccidentRepository memoryAccidentRepository;
    private final SimpleRuleService simpleRuleService;
    private final SimpleAccidentTypeService simpleAccidentTypeService;

    @Override
    public Accident add(Accident accident) {
        return memoryAccidentRepository.add(accident);
    }

    @Override
    public boolean update(Accident accident, Set<Integer> rIds) {
        Set<Rule> rules = rIds.stream()
                .map(x -> simpleRuleService.findById(x).get())
                .collect(Collectors.toSet());
        Optional<AccidentType> accidentTypeOptional = simpleAccidentTypeService.findById(accident.getType().getId());
        accident.setType(accidentTypeOptional.get());
        accident.setRules(rules);
        return accident.getId() == -1 ? add(accident) != null : memoryAccidentRepository.update(accident);
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
