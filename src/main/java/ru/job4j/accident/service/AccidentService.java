package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentMem;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AccidentService {
    private AccidentMem accidents;

    public AccidentService(AccidentMem accidents) {
        this.accidents = accidents;
    }

    public List<Accident> getAccidents() {
        return accidents.getAccidents();
    }

    public List<AccidentType> getTypes() {
        return accidents.getTypes();
    }

    public List<Rule> getRules() {
        return accidents.getRules();
    }

    public void create(Accident accident) {
        AccidentType type = accidents.getTypeById(accident.getType().getId());
        accident.setType(type);
        accidents.create(accident);
    }

    public Accident getAccidentById(int id) {
        return accidents.getAccidentById(id);
    }

    public Set<Rule> getRulesById(String[] ids) {
        return accidents.getRulesById(ids);
    }
}
