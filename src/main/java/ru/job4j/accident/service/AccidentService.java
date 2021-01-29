package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentHibernate;
import ru.job4j.accident.repository.Store;

import java.util.List;

@Service
public class AccidentService implements ru.job4j.accident.service.Service {
    private Store accidents;

    public AccidentService(AccidentHibernate accidents) {
        this.accidents = accidents;
    }

    @Override
    public List<Accident> getAccidents() {
        return accidents.getAccidents();
    }

    @Override
    public List<AccidentType> getTypes() {
        return accidents.getTypes();
    }

    @Override
    public List<Rule> getRules() {
        return accidents.getRules();
    }

    @Override
    public void save(Accident accident, String[] ids) {
        accident.setRules(accidents.getRulesByIds(ids));
        accidents.save(accident);
    }

    @Override
    public Accident getAccidentById(int id) {
        return accidents.getAccidentById(id);
    }
}
