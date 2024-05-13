package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Primary
public class HibernateAccidentRepository implements AccidentRepository {

    private final SessionFactory sessionFactory;

    @Override
    public boolean update(Accident accident) {
        try (Session session = sessionFactory.openSession()) {
            session.merge(accident);
            return true;
        }
    }

    @Override
    public Optional<Accident> add(Accident accident) {
        try (Session session = sessionFactory.openSession()) {
            session.save(accident);
            return Optional.of(accident);
        }
    }

    @Override
    public Optional<Accident> findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.of(session.find(Accident.class, id));
        }
    }

    @Override
    public Collection<Accident> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery("FROM Accident", Accident.class)
                    .list();
        }

    }

}