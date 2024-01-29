package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.DataRuleRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Service
@Primary
@AllArgsConstructor
public class SimpleDataRuleService implements RuleService {

    private final DataRuleRepository dataRuleRepository;

    @Override
    public Collection<Rule> findAll() {
        Collection<Rule> collection = new ArrayList<>();
        dataRuleRepository.findAll().forEach(collection::add);
        return collection;
    }

    @Override
    public Collection<Rule> findAllByIds(Set<Integer> ids) {
        Collection<Rule> collection = new ArrayList<>();
        dataRuleRepository.findAllById(ids).forEach(collection::add);
        return collection;
    }

}
