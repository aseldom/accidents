package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class MemoryAccidentRepository implements AccidentRepository {

    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();
    private final AtomicInteger atomicInt = new AtomicInteger(1);

    public MemoryAccidentRepository() {
        MemoryRuleRepository memoryRuleRepository = new MemoryRuleRepository();
        MemoryAccidentTypeRepository memoryAccidentTypeRepository = new MemoryAccidentTypeRepository();
        add(new Accident(1, "Name 1", "Text 1", "Address 1",
                memoryAccidentTypeRepository.findById(1).get(),
                new HashSet<>(memoryRuleRepository.findAllByIds(new HashSet<>(1)))));
        add(new Accident(1, "Name 2", "Text 2", "Address 2",
                memoryAccidentTypeRepository.findById(1).get(),
                new HashSet<>(memoryRuleRepository.findAllByIds(new HashSet<>(1)))));
        add(new Accident(1, "Name 3", "Text 3", "Address 3",
                memoryAccidentTypeRepository.findById(1).get(),
                new HashSet<>(memoryRuleRepository.findAllByIds(new HashSet<>(1)))));
        add(new Accident(1, "Name 4", "Text 4", "Address 4",
                memoryAccidentTypeRepository.findById(1).get(),
                new HashSet<>(memoryRuleRepository.findAllByIds(new HashSet<>(1)))));
    }

    @Override
    public Optional<Accident> add(Accident accident) {
        accident.setId(atomicInt.getAndIncrement());
        accidents.putIfAbsent(accident.getId(), accident);
        return Optional.of(accident);
    }

    @Override
    public boolean update(Accident accident) {
        return accidents.computeIfPresent(accident.getId(), (id, oldAccident) ->
                new Accident(
                        oldAccident.getId(),
                        accident.getName(),
                        accident.getText(),
                        accident.getAddress(),
                        accident.getType(),
                        accident.getRules()
                )
        ) != null;
    }

    @Override
    public Collection<Accident> findAll() {
        return accidents.values();
    }

    @Override
    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(accidents.get(id));
    }

}
