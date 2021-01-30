package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentRepository;
import ru.job4j.accident.repository.RuleRepository;
import ru.job4j.accident.repository.TypeRepository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class AccidentServiceForSpringData implements ru.job4j.accident.service.Service {
    private AccidentRepository accidentRepository;
    private TypeRepository typeRepository;
    private RuleRepository ruleRepository;

    public AccidentServiceForSpringData(AccidentRepository accidentRepository,
                                        TypeRepository typeRepository,
                                        RuleRepository ruleRepository) {
        this.accidentRepository = accidentRepository;
        this.typeRepository = typeRepository;
        this.ruleRepository = ruleRepository;

    }

    @Override
    public List<Accident> getAccidents() {
        return (List<Accident>) accidentRepository.findAll();
    }

    @Override
    public List<AccidentType> getTypes() {
        return (List<AccidentType>) typeRepository.findAll();
    }

    @Override
    public List<Rule> getRules() {
        return (List<Rule>) ruleRepository.findAll();
    }

    @Override
    public void save(Accident accident, String[] ids) {
        if (accident.getId() == 0) {
            accident = accidentRepository.save(accident);
        }
        accident.setRules(getRulesForAccidentByIds(ids));
        accidentRepository.save(accident);
    }

    @Override
    public Accident getAccidentById(int id) {
        return accidentRepository.findById(id).get();
    }

    private Set<Rule> getRulesForAccidentByIds(String[] ids) {
        int[] numIds = Arrays.stream(ids).mapToInt(Integer::parseInt).toArray();
        List<Integer> params = IntStream.of(numIds).boxed().collect(Collectors.toList());
        return new HashSet<>((List<Rule>) ruleRepository.findAllById(params));
    }
}
