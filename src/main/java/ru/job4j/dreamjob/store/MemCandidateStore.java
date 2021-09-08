package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Candidate;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemCandidateStore implements CandidateStore {

    private static final MemCandidateStore INSTANCE = new MemCandidateStore();

    private final AtomicInteger generator;
    private final Map<Integer, Candidate> candidates;

    private MemCandidateStore() {
        candidates = new ConcurrentHashMap<>();
        generator = new AtomicInteger(0);
    }

    public static MemCandidateStore getInstance() {
        return INSTANCE;
    }

    @Override
    public Collection<Candidate> findAll() {
        return candidates.values();
    }

    @Override
    public Candidate getById(int id) {
        return candidates.get(id);
    }

    @Override
    public void save(Candidate value) {
        if (value.getId() == 0) {
            value.setId(generator.incrementAndGet());
        }
        candidates.put(value.getId(), value);
    }

    @Override
    public void delete(int id) {
        if (candidates.remove(id) != null) {
            FilesImageStore imgStore = FilesImageStore.getInstance();
            imgStore.delete(id);
        }
    }
}
