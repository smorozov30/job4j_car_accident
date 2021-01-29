package ru.job4j.accident.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Repository
public class AccidentHibernate implements Store {
    private final SessionFactory sf;

    public AccidentHibernate(SessionFactory sf) {
        this.sf = sf;
    }

    @Override
    public List<Accident> getAccidents() {
        return execute(session -> session.createQuery(
                                    "SELECT DISTINCT a FROM Accident a " +
                                        "JOIN FETCH a.type " +
                                        "JOIN FETCH a.rules "
                                    ).list()
                        );
    }

    @Override
    public void save(Accident accident) {
        if (accident.getId() == 0) {
            execute(session -> session.save(accident));
        } else {
            execute(session -> {
                session.update(accident);
                return accident;
            });
        }
    }

    @Override
    public List<AccidentType> getTypes() {
        return execute(session -> session.createQuery("FROM AccidentType ").list());
    }

    @Override
    public List<Rule> getRules() {
        return execute(session -> session.createQuery("FROM Rule ORDER BY id").list());
    }

    @Override
    public Accident getAccidentById(int id) {
        return (Accident) execute(session -> session.createQuery(
                                            "SELECT DISTINCT a FROM Accident a " +
                                                "JOIN FETCH a.type " +
                                                "JOIN FETCH a.rules " +
                                            "WHERE a.id = :id")
                                        .setParameter("id", id)
                                        .uniqueResult()
                                );
    }

    @Override
    public Set<Rule> getRulesByIds(String[] ids) {
        int[] numIds = Arrays.stream(ids).mapToInt(Integer::parseInt).toArray();
        List<Integer> params = IntStream.of(numIds).boxed().collect(Collectors.toList());
        return new HashSet<>(execute(session -> session.createQuery(
                                            "FROM Rule WHERE id IN :ids")
                                                .setParameter("ids", params)
                                                .getResultList()
                                    )
                            );
    }

    private <T> T execute(final Function<Session, T> command) {
        T result = null;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            result = command.apply(session);
            session.getTransaction().commit();
        }
        return result;
    }
}
