package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class JdbcRuleRepository implements RuleRepository {

    private final JdbcTemplate jdbc;

    @Override
    public Collection<Rule> findAllByIds(Set<Integer> ids) {
        String idsPlaceholder = ids.stream().map(Object::toString).collect(Collectors.joining(", "));
        return jdbc.query("select id, name from rules where id IN (" + idsPlaceholder + ")",
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    return rule;
                });
    }

    @Override
    public Collection<Rule> findAll() {
        return jdbc.query("select id, name from rules",
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    return rule;
                });
    }

}