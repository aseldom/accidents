package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleRepository;

import java.util.Collection;
import java.util.Set;

@Service
@AllArgsConstructor
public class SimpleRuleService implements RuleService {

    private RuleRepository ruleRepository;

    @Override
    public Collection<Rule> findAll() {
        return ruleRepository.findAll();
    }

    @Override
    public Collection<Rule> findAllByIds(Set<Integer> ids) {
        return ruleRepository.findAllByIds(ids);
    }

}
