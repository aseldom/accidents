package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Primary
public class HibernateAccidentTypeRepository implements AccidentTypeRepository {

    private final SessionFactory sessionFactory;

    @Override
    public Optional<AccidentType> findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.of(session.find(AccidentType.class, id));
        }
    }

    @Override
    public Collection<AccidentType> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery("FROM AccidentType", AccidentType.class)
                    .list();
        }
    }

}