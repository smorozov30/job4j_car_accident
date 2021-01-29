package ru.job4j.accident.service;

import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.List;

public interface Service {
    List<Accident> getAccidents();
    List<AccidentType> getTypes();
    List<Rule> getRules();
    void save(Accident accident, String[] ids);
    Accident getAccidentById(int id);
}
