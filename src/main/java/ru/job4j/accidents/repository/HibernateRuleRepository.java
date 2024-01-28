package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Repository
@AllArgsConstructor
@Primary
public class HibernateRuleRepository implements RuleRepository {

    private final CrudRepository crudRepository;

    @Override
    public Collection<Rule> findAllByIds(Set<Integer> ids) {
        return crudRepository.query("from Rule where id IN (:ids)",
                Rule.class,
                Map.of("ids", ids)
        );
    }

        @Override
        public Collection<Rule> findAll() {
            return crudRepository.query("FROM Rule", Rule.class);
        }

}