package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.MemoryRuleRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleRuleService implements RuleService {

    private MemoryRuleRepository memoryRuleRepository;

    @Override
    public Rule add(Rule rule) {
        return memoryRuleRepository.add(rule);
    }

    @Override
    public boolean update(Rule rule) {
        return memoryRuleRepository.update(rule);
    }

    @Override
    public Optional<Rule> findById(int id) {
        return memoryRuleRepository.findById(id);
    }

    @Override
    public Collection<Rule> findAll() {
        return memoryRuleRepository.findAll();
    }

}
