package ru.job4j.accidents.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@Component
public class AccidentRowMapper implements RowMapper<Accident> {

    @Override
    public Accident mapRow(ResultSet rs, int rowNum) throws SQLException {
        Accident accident = new Accident();
        accident.setId(rs.getInt("accident_id"));
        accident.setName(rs.getString("name"));
        accident.setText(rs.getString("text"));
        accident.setAddress(rs.getString("address"));

        AccidentType type = new AccidentType();
        type.setId(rs.getInt("type_id"));
        type.setName(rs.getString("type_name"));
        accident.setType(type);

        Set<Rule> rules = new HashSet<>();
        do {
            Rule rule = new Rule();
            rule.setId(rs.getInt("rule_id"));
            rule.setName(rs.getString("rule_name"));
            rules.add(rule);
        } while (rs.next());
        accident.setRules(rules);
        return accident;
    }

}
