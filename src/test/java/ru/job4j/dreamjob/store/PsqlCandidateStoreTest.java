package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.job4j.dreamjob.AppSettings;
import ru.job4j.dreamjob.model.Candidate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class PsqlCandidateStoreTest {

    private static BasicDataSource pool;

    @BeforeClass
    public static void setup() {
        pool = AppSettings.getConnectionPool();
    }

    @After
    public void cleanTable() throws SQLException {
        String query = "DELETE FROM tz_candidates";
        try (
                Connection conn = pool.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.executeUpdate();
        }
    }

    @Test
    public void whenCreateCandidate() {
        CandidateStore store = PsqlCandidateStore.getInstance();
        Candidate candidate = new Candidate(0, "Петров Валерий Николаевич", "Java middle developer");
        store.save(candidate);
        Candidate founded = store.getById(candidate.getId());
        assertEquals(candidate.getName(), founded.getName());
    }

    @Test
    public void whenUpdateCandidate() {
        CandidateStore store = PsqlCandidateStore.getInstance();
        Candidate candidate = new Candidate(0, "Петров Валерий Николаевич", "Java middle developer");
        store.save(candidate);
        int id = candidate.getId();
        candidate = store.getById(id);
        candidate.setName("Баширов Валерий Сергеевич");
        candidate.setCityId(null);
        store.save(candidate);
        candidate = store.getById(id);
        assertEquals("Баширов Валерий Сергеевич", candidate.getName());
    }

    @Test
    public void whenDeleteCandidate() {
        CandidateStore store = PsqlCandidateStore.getInstance();
        Candidate candidate = new Candidate(0, "Тот-кому-надо-подучится", "Java middle developer");
        store.save(candidate);
        int id = candidate.getId();
        store.delete(id);
        candidate = store.getById(id);
        assertNull(candidate);
    }

}