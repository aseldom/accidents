package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Primary
public class JdbcAccidentTypeRepository implements AccidentTypeRepository {

    private final JdbcTemplate jdbc;

    @Override
    public Optional<AccidentType> findById(int id) {
        return Optional.ofNullable(
                jdbc.queryForObject("select id, name from types where id = ?",
                        (rs, row) -> {
                            AccidentType accidentType = new AccidentType();
                            accidentType.setId(rs.getInt("id"));
                            accidentType.setName(rs.getString("name"));
                            return accidentType;
                        },
                        id)
        );
    }

    @Override
    public Collection<AccidentType> findAll() {
        return jdbc.query("select id, name from types",
                (rs, row) -> {
                    AccidentType accidentType = new AccidentType();
                    accidentType.setId(rs.getInt("id"));
                    accidentType.setName(rs.getString("name"));
                    return accidentType;
                });
    }

}