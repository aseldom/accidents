package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.DataRuleRepository;

import java.util.Collection;
import java.util.Set;

@Service
@Primary
@AllArgsConstructor
public class SimpleDataRuleService implements RuleService {

    private final DataRuleRepository dataRuleRepository;

    @Override
    public Collection<Rule> findAll() {
        return dataRuleRepository.findAll();
    }

    @Override
    public Collection<Rule> findAllByIds(Set<Integer> ids) {
        return dataRuleRepository.findAllById(ids);
    }

}
