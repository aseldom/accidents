package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class MemoryRuleRepository implements RuleRepository {

    private final Map<Integer, Rule> types = new ConcurrentHashMap<>();
    private final AtomicInteger atomicInt = new AtomicInteger(1);

    public MemoryRuleRepository() {
        add(new Rule(1, "Две машины"));
        add(new Rule(2, "Машина и человек"));
        add(new Rule(3, "Машина и велосипед"));
    }

    @Override
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
    public Optional<Rule> findById(int id) {
        return Optional.ofNullable(types.get(id));
    }

    @Override
    public boolean update(Rule rule) {
        return types.computeIfPresent(rule.getId(), (id, oldAccident) ->
                new Rule(
                        oldAccident.getId(),
                        rule.getName()
                )
        ) != null;
    }

}
