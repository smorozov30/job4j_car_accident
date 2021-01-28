package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.repository.AccidentMem;

import java.util.List;

@Service
public class AccidentService {
    private AccidentMem accidents;

    public AccidentService(AccidentMem accidents) {
        this.accidents = accidents;
    }

    public List<Accident> getAccidents() {
        return accidents.getAccidents();
    }

    public void create(Accident accident) {
        accidents.create(accident);
    }

    public Accident getAccidentById(int id) {
        return accidents.getAccidentById(id);
    }
}
