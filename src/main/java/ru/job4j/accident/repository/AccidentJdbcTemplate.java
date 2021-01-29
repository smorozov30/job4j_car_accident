package ru.job4j.accident.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.sql.PreparedStatement;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class AccidentJdbcTemplate implements Store {
    private final JdbcTemplate jdbc;

    public AccidentJdbcTemplate(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Accident> getAccidents() {
        return jdbc.query("SELECT id, name, text, address, type_id FROM accident ORDER BY id",
                (rs, row) -> {
                    Accident accident = Accident.of(rs.getInt("id"),
                                                    rs.getString("name"),
                                                    rs.getString("text"),
                                                    rs.getString("address")
                                                );
                    accident.setType(getTypeById(rs.getInt("type_id")));
                    accident.setRules(getRulesByAccidentId(accident.getId()));
                    return accident;
                });
    }

    @Override
    public void save(Accident accident) {
        if (accident.getId() == 0) {
            create(accident);
        } else {
            update(accident);
        }
    }

    @Override
    public List<AccidentType> getTypes() {
        return jdbc.query(
                "SELECT id, name FROM type",
                (rs, row) -> AccidentType.of(rs.getInt("id"), rs.getString("name"))
        );
    }

    @Override
    public List<Rule> getRules() {
        return jdbc.query(
                "SELECT id, name FROM rule",
                (rs, row) -> Rule.of(rs.getInt("id"), rs.getString("name"))
        );
    }

    @Override
    public Accident getAccidentById(int id) {
        return jdbc.queryForObject(
                "SELECT id, name, text, address, type_id FROM accident WHERE id = ?",
                (rs, row) -> {
                    Accident accident = Accident.of(rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("text"),
                            rs.getString("address")
                    );
                    accident.setType(getTypeById(rs.getInt("type_id")));
                    accident.setRules(getRulesByAccidentId(accident.getId()));
                    return accident;
                },
                id
        );
    }

    @Override
    public Set<Rule> getRulesByIds(String[] ids) {
        Set<Rule> result = new HashSet<>();
        for (String id : ids) {
            result.add(jdbc.queryForObject(
                    "SELECT id, name FROM rule WHERE id = ?",
                    (rs, row) -> Rule.of(rs.getInt("id"),rs.getString("name")),
                    Integer.parseInt(id)
            ));
        }
        return result;
    }

    private void create(Accident accident) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement statement = con.prepareStatement(
                    "INSERT INTO accident (name, text, address, type_id) VALUES (?, ?, ?, ?)",
                    new String[] { "id" }
            );
            statement.setString(1, accident.getName());
            statement.setString(2, accident.getText());
            statement.setString(3, accident.getAddress());
            statement.setInt(4, accident.getType().getId());
            return statement;
        }, keyHolder);
        accident.setId(keyHolder.getKey().intValue());
        linkAccidentAndRules(accident);
    }

    private void update(Accident accident) {
        jdbc.update(
                "UPDATE accident SET name = ?, text = ?, address = ?, type_id = ? WHERE id = ?",
                accident.getName(),
                accident.getText(),
                accident.getAddress(),
                accident.getType().getId(),
                accident.getId());
        jdbc.update("DELETE FROM accident_rule WHERE accident_id = ?", accident.getId());
        linkAccidentAndRules(accident);
    }

    private AccidentType getTypeById(int id) {
        return jdbc.queryForObject(
                "SELECT id, name FROM type WHERE id = ?",
                (rs, row) -> AccidentType.of(rs.getInt("id"), rs.getString("name")),
                id
        );
    }

    private Set<Rule> getRulesByAccidentId(int id) {
        return new HashSet<>(jdbc.query(
                    "SELECT r.id AS \"r.id\", r.name AS \"r.name\" " +
                        "FROM rule r " +
                        "JOIN accident_rule ar " +
                        "ON r.id = ar.rule_id AND ar.accident_id = ?",
                (rs, row) -> Rule.of(rs.getInt("r.id"), rs.getString("r.name")), id));
    }

    private void linkAccidentAndRules(Accident accident) {
        for (Rule rule : accident.getRules()) {
            jdbc.update("INSERT INTO accident_rule (accident_id, rule_id) values (?, ?)",
                    accident.getId(),
                    rule.getId());
        }
    }
}