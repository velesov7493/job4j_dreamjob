package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Candidate;

import java.util.*;
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
    public Collection<Candidate> findAllCreatedToday() {
        List<Candidate> result = new ArrayList<>();
        Calendar cl = Calendar.getInstance();
        Calendar clLeft = Calendar.getInstance();
        Calendar clRight = Calendar.getInstance();
        clLeft.set(cl.get(
                Calendar.YEAR), cl.get(Calendar.MONTH), cl.get(Calendar.DATE),
                0, 0, 0
        );
        clRight.set(cl.get(
                Calendar.YEAR), cl.get(Calendar.MONTH), cl.get(Calendar.DATE),
                23, 59, 59
        );
        for (Integer key : candidates.keySet()) {
            Candidate value = candidates.get(key);
            if (
                    value.getCreated().after(clLeft.getTime())
                    && value.getCreated().before(clRight.getTime())
            ) {
                result.add(value);
            }
        }
        return result;
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
