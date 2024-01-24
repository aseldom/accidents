package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class MemoryAccidentRepository implements AccidentRepository {

    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();
    private final AtomicInteger atomicInt = new AtomicInteger(1);

    private MemoryAccidentRepository() {
        add(new Accident(1, "Name 1", "Text 1", "Address 1"));
        add(new Accident(1, "Name 2", "Text 2", "Address 2"));
        add(new Accident(1, "Name 3", "Text 3", "Address 3"));
        add(new Accident(1, "Name 4", "Text 4", "Address 4"));
    }

    @Override
    public Accident add(Accident accident) {
        accident.setId(atomicInt.getAndIncrement());
        accidents.putIfAbsent(accident.getId(), accident);
        return accident;
    }

    @Override
    public Collection<Accident> findAll() {
        return accidents.values();
    }

    @Override
    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(accidents.get(id));
    }

    @Override
    public boolean update(Accident accident) {
        return accidents.computeIfPresent(accident.getId(), (id, oldAccident) ->
                new Accident(
                        oldAccident.getId(),
                        accident.getName(),
                        accident.getText(),
                        accident.getAddress()
                )
        ) != null;
    }

}
