package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;

import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class JdbcAccidentRepository implements AccidentRepository {

    private final JdbcTemplate jdbc;
    private final AccidentRowMapper rowMapper;

    @Override
    public Optional<Accident> add(Accident accident) {
        Accident res = accident;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into accidents (name, text, address, type_id) values (?, ?, ?, ?)",
                    new String[]{"id"}
            );
            ps.setString(1, accident.getName());
            ps.setString(2, accident.getText());
            ps.setString(3, accident.getAddress());
            ps.setLong(4, accident.getType().getId());
            return ps;
        }, keyHolder);
        Number generatedId = keyHolder.getKey();
        if (generatedId != null) {
            accident.setId(generatedId.intValue());
            setRules(accident);
        } else {
            res = null;
        }
        return Optional.ofNullable(res);
    }

    @Override
    public boolean update(Accident accident) {
        jdbc.update(
                "delete from accidents_rules where accidents_id = ?",
                accident.getId()
        );
        boolean res = setRules(accident);
        return res && jdbc.update("update accidents set name = ?, text = ?, address = ? , type_id = ? where id = ?",
                accident.getName(),
                accident.getText(),
                accident.getAddress(),
                accident.getType().getId(),
                accident.getId()) > 0;
    }

    @Override
    public Optional<Accident> findById(int id) {
        String sql = "SELECT a.id AS accident_id, a.name, a.text, a.address, "
                + "t.id AS type_id, t.name AS type_name, "
                + "r.id AS rule_id, r.name AS rule_name "
                + "FROM accidents a "
                + "JOIN types t ON a.type_id = t.id "
                + "LEFT JOIN accidents_rules ar ON a.id = ar.accidents_id "
                + "LEFT JOIN rules r ON ar.rules_id = r.id "
                + "WHERE a.id = ?";
        return Optional.ofNullable(jdbc.queryForObject(sql, new Object[]{id}, rowMapper));
    }

    @Override
    public Collection<Accident> findAll() {
        return jdbc.query("select id, name, text, address from accidents",
                (rs, row) -> {
                    Accident accident = new Accident();
                    accident.setId(rs.getInt("id"));
                    accident.setName(rs.getString("name"));
                    accident.setText(rs.getString("text"));
                    accident.setAddress(rs.getString("address"));
                    return accident;
                });
    }

    private boolean setRules(Accident accident) {
        boolean res = true;
        for (Rule rule : accident.getRules()) {
            res &= jdbc.update(
                    "insert into accidents_rules (accidents_id, rules_id) values (?, ?)",
                    accident.getId(),
                    rule.getId()
            ) > 0;
        }
        return res;
    }

}