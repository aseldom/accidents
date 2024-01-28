package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Primary
public class HibernateAccidentRepository implements AccidentRepository {

    private final CrudRepository crudRepository;

    @Override
    public boolean update(Accident accident) {
        crudRepository.run(session -> session.merge(accident));
        return true;
    }

    @Override
    public Optional<Accident> add(Accident accident) {
        crudRepository.run(session -> session.save(accident));
        return Optional.of(accident);
    }

    @Override
    public Optional<Accident> findById(int id) {
        return crudRepository.optional("from Accident a join fetch a.rules where a.id = :fId",
                Accident.class,
                Map.of("fId", id)
        );
    }

    @Override
    public Collection<Accident> findAll() {
        return crudRepository.query("FROM Accident", Accident.class);
    }

}