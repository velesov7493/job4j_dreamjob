package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Candidate;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CandidateStore {

    private static final CandidateStore INSTANCE = new CandidateStore();

    private final AtomicInteger generator;
    private final Map<Integer, Candidate> candidates;

    private CandidateStore() {
        candidates = new ConcurrentHashMap<>();
        candidates.put(1, new Candidate(
                1,
                "Иванов Иван Иванович",
                "Младший java-разработчик"
        ));
        candidates.put(2, new Candidate(
                2,
                "Петров Валерий Николаевич",
                "Обычный java-программист"
        ));
        candidates.put(3, new Candidate(
                3,
                "Баширов Валерий Павлович",
                "Старший java-программист"
        ));
        generator = new AtomicInteger(3);
    }

    public static CandidateStore getInstance() {
        return INSTANCE;
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }

    public Candidate getById(int id) {
        return candidates.get(id);
    }

    public void save(Candidate value) {
        if (value.getId() == 0) {
            value.setId(generator.incrementAndGet());
        }
        candidates.put(value.getId(), value);
    }

    public void delete(int id) {
        if (candidates.remove(id) != null) {
            ImageStore imgStore = ImageStore.getInstance();
            imgStore.delete(id);
        }
    }
}
