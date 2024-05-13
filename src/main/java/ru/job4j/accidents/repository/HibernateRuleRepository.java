package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.Set;

@Repository
@AllArgsConstructor
@Primary
public class HibernateRuleRepository implements RuleRepository {

    private final SessionFactory sessionFactory;

    @Override
    public Collection<Rule> findAllByIds(Set<Integer> ids) {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery("FROM Rule WHERE id IN (:ids)", Rule.class)
                    .setParameter("ids", ids)
                    .list();
        }
    }

    @Override
    public Collection<Rule> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery("FROM Rule", Rule.class)
                    .list();
        }
    }

}