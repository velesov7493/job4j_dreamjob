package ru.job4j.dreamjob.servlet;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import ru.job4j.dreamjob.AppSettings;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.store.CandidateStore;
import ru.job4j.dreamjob.store.MemCandidateStore;
import ru.job4j.dreamjob.store.PsqlCandidateStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class CandidateServletTest {

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
    public void whenCreateCandidate() throws ServletException, IOException {
        CandidateStore store = PsqlCandidateStore.getInstance();
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        Part idPart = mock(Part.class);
        Part cityIdPart = mock(Part.class);
        Part namePart = mock(Part.class);
        Part positionPart = mock(Part.class);
        Mockito.when(idPart.getInputStream()).thenReturn(new ByteArrayInputStream("0".getBytes(StandardCharsets.UTF_8)));
        Mockito.when(namePart.getInputStream()).thenReturn(new ByteArrayInputStream("???????????? ?????????????? ????????????????????".getBytes(StandardCharsets.UTF_8)));
        Mockito.when(positionPart.getInputStream()).thenReturn(new ByteArrayInputStream("Java middle job".getBytes(StandardCharsets.UTF_8)));
        Mockito.when(cityIdPart.getInputStream()).thenReturn(new ByteArrayInputStream("null".getBytes(StandardCharsets.UTF_8)));
        Mockito.when(req.getPart("nCandidateId")).thenReturn(idPart);
        Mockito.when(req.getPart("nSelectedCityId")).thenReturn(cityIdPart);
        Mockito.when(req.getPart("nName")).thenReturn(namePart);
        Mockito.when(req.getPart("nPosition")).thenReturn(positionPart);
        Mockito.when(req.getPart("nPhoto")).thenReturn(null);
        new CandidateServlet().doPost(req, resp);
        Candidate result = store.findAll().iterator().next();
        Assert.assertThat(result.getName(), Is.is("???????????? ?????????????? ????????????????????"));
        Assert.assertThat(result.getPosition(), Is.is("Java middle job"));
    }
}