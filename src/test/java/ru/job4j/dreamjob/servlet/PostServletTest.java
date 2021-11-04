package ru.job4j.dreamjob.servlet;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hamcrest.core.Is;
import org.junit.*;
import org.mockito.Mockito;
import ru.job4j.dreamjob.AppSettings;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.store.MemPostStore;
import ru.job4j.dreamjob.store.PostStore;
import ru.job4j.dreamjob.store.PsqlPostStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.mockito.Mockito.mock;

public class PostServletTest {

    private static BasicDataSource pool;

    @BeforeClass
    public static void setup() {
        pool = AppSettings.getConnectionPool();
    }

    @After
    public void cleanTable() throws SQLException {
        String query = "DELETE FROM tz_posts";
        try (
                Connection conn = pool.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.executeUpdate();
        }
    }

    @Test
    public void whenCreatePost() throws IOException, ServletException {
        PostStore store = PsqlPostStore.getInstance();
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        Mockito.when(req.getParameter("id")).thenReturn("0");
        Mockito.when(req.getParameter("nPosition")).thenReturn("Java middle");
        Mockito.when(req.getParameter("nDescription")).thenReturn("Java middle job");
        new PostServlet().doPost(req, resp);
        Post result = store.findAll().iterator().next();
        Assert.assertThat(result.getName(), Is.is("Java middle"));
        Assert.assertThat(result.getDescription(), Is.is("Java middle job"));
    }


}