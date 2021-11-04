package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.job4j.dreamjob.AppSettings;
import ru.job4j.dreamjob.model.Post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class PsqlPostStoreTest {

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
    public void whenCreatePost() {
        PostStore store = PsqlPostStore.getInstance();
        Post post = new Post(0, "Java Job");
        store.save(post);
        Post postInDb = store.getById(post.getId());
        assertEquals(post.getName(), postInDb.getName());
    }

    @Test
    public void whenUpdatePost() {
        PostStore store = PsqlPostStore.getInstance();
        Post post = new Post(0, "Updatable post");
        store.save(post);
        int id = post.getId();
        post = store.getById(id);
        post.setName("Edited post name");
        store.save(post);
        post = store.getById(id);
        assertEquals("Edited post name", post.getName());
    }

    @Test
    public void whenDeletePost() {
        PostStore store = PsqlPostStore.getInstance();
        Post post = new Post(0, "Deletable post");
        store.save(post);
        int id = post.getId();
        store.delete(id);
        post = store.getById(id);
        assertNull(post);
    }
}