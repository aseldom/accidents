package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Primary
public class HibernateAccidentTypeRepository implements AccidentTypeRepository {

    private final CrudRepository crudRepository;

    @Override
    public Optional<AccidentType> findById(int id) {
        return crudRepository.optional("FROM AccidentType WHERE id = :fId",
                AccidentType.class,
                Map.of("fId", id)
        );
    }

    @Override
    public Collection<AccidentType> findAll() {
        return crudRepository.query("FROM AccidentType", AccidentType.class);
    }

}