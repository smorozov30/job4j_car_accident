package ru.job4j.accident.repository;

import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.List;
import java.util.Set;

public interface Store {
    List<Accident> getAccidents();
    void save(Accident accident);
    List<AccidentType> getTypes();
    List<Rule> getRules();
    Accident getAccidentById(int id);
    Set<Rule> getRulesByIds(String[] ids);
}
