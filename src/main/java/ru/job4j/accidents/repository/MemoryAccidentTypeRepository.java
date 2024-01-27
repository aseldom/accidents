package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class MemoryAccidentTypeRepository implements AccidentTypeRepository {

    private final Map<Integer, AccidentType> types = new ConcurrentHashMap<>();
    private final AtomicInteger atomicInt = new AtomicInteger(1);

    public MemoryAccidentTypeRepository() {
        add(new AccidentType(1, "Две машины"));
        add(new AccidentType(2, "Машина и человек"));
        add(new AccidentType(3, "Машина и велосипед"));
    }

    public AccidentType add(AccidentType accidentType) {
        accidentType.setId(atomicInt.getAndIncrement());
        types.putIfAbsent(accidentType.getId(), accidentType);
        return accidentType;
    }

    @Override
    public Collection<AccidentType> findAll() {
        return types.values();
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        return Optional.ofNullable(types.get(id));
    }

}
