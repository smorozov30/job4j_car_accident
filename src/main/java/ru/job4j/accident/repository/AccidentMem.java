package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {
    private static final AtomicInteger ACCIDENT_ID = new AtomicInteger(0);

    private Map<Integer, Accident> accidents;

    public AccidentMem(Map<Integer, Accident> accidents) {
        this.accidents = accidents;
        accidents.put(ACCIDENT_ID.incrementAndGet(), new Accident(1, "one", "description 1", "address 1"));
        accidents.put(ACCIDENT_ID.incrementAndGet(), new Accident(2, "two", "description 2", "address 2"));
        accidents.put(ACCIDENT_ID.incrementAndGet(), new Accident(3, "three", "description 3", "address 3"));
        accidents.put(ACCIDENT_ID.incrementAndGet(), new Accident(4, "four", "description 4", "address 4"));
        accidents.put(ACCIDENT_ID.incrementAndGet(), new Accident(5, "five", "description 5", "address 5"));
    }

    public List<Accident> getAccidents() {
        return accidents != null ? new ArrayList<>(accidents.values()) : new ArrayList<>();
    }
}
