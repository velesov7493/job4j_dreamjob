package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Candidate;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CandidateStore {

    private static final CandidateStore INSTANCE = new CandidateStore();

    private final Map<Integer, Candidate> candidates;

    private CandidateStore() {
        candidates = new ConcurrentHashMap<>();
        candidates.put(1, new Candidate(
                1,
                "Иванов Иван Иванович",
                "Младший java-разработчик")
        );
        candidates.put(2, new Candidate(
                2,
                "Петров Валерий Николаевич",
                "Обычный java-программист")
        );
        candidates.put(3, new Candidate(
                3,
                "Баширов Валерий Павлович",
                "Старший java-программист")
        );
    }

    public static CandidateStore getInstance() {
        return INSTANCE;
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }
}
