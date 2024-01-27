package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class MemoryRuleRepository implements RuleRepository {

    private final Map<Integer, Rule> types = new ConcurrentHashMap<>();
    private final AtomicInteger atomicInt = new AtomicInteger(1);

    public MemoryRuleRepository() {
        add(new Rule(1, "Статья. 1"));
        add(new Rule(2, "Статья. 2"));
        add(new Rule(3, "Статья. 3"));
    }

    public Rule add(Rule rule) {
        rule.setId(atomicInt.getAndIncrement());
        types.putIfAbsent(rule.getId(), rule);
        return rule;
    }

    @Override
    public Collection<Rule> findAll() {
        return types.values();
    }

    @Override
    public Collection<Rule> findAllByIds(Set<Integer> ids) {
        return ids.stream()
                .map(types::get)
                .collect(Collectors.toSet());

    }

}
