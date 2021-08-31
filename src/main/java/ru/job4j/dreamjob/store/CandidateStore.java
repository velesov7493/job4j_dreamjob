package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Candidate;

import java.util.Collection;

public interface CandidateStore {

    Collection<Candidate> findAll();

    Candidate getById(int id);

    void save(Candidate value);

    void delete(int id);
}
