package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {
    private static final AtomicInteger ACCIDENT_ID = new AtomicInteger(0);

    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();
    private final Map<Integer, AccidentType> types = new ConcurrentHashMap<>();

    {
        accidents.put(ACCIDENT_ID.incrementAndGet(), new Accident(1, "one", "description 1", "address 1"));
        accidents.put(ACCIDENT_ID.incrementAndGet(), new Accident(2, "two", "description 2", "address 2"));
        accidents.put(ACCIDENT_ID.incrementAndGet(), new Accident(3, "three", "description 3", "address 3"));
        accidents.put(ACCIDENT_ID.incrementAndGet(), new Accident(4, "four", "description 4", "address 4"));
        accidents.put(ACCIDENT_ID.incrementAndGet(), new Accident(5, "five", "description 5", "address 5"));
        types.put(1, AccidentType.of(1, "Две машины"));
        types.put(2, AccidentType.of(2, "Машина и человек"));
        types.put(3, AccidentType.of(3, "Машина и велосипед"));
    }

    public List<Accident> getAccidents() {
        return new ArrayList<>(accidents.values());
    }

    public List<AccidentType> getTypes() {
        return new ArrayList<>(types.values());
    }

    public void create(Accident accident) {
        int id = accident.getId();
        if (id == 0) {
            id = ACCIDENT_ID.incrementAndGet();
            accident.setId(id);
        }
        accidents.put(id, accident);
    }

    public Accident getAccidentById(int id) {
        return accidents.get(id);
    }

    public AccidentType getTypeById(int id) {
        return types.get(id);
    }
}
