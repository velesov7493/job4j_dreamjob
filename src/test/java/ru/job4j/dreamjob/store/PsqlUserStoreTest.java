package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.job4j.dreamjob.AppSettings;
import ru.job4j.dreamjob.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class PsqlUserStoreTest {

    private static BasicDataSource pool;

    @BeforeClass
    public static void setup() {
        pool = AppSettings.getConnectionPool();
    }

    @After
    public void cleanTable() throws SQLException {
        String query = "DELETE FROM tz_users";
        try (
                Connection conn = pool.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.executeUpdate();
        }
    }

    @Test
    public void whenCreateUser() {
        UserStore store = PsqlUserStore.getInstance();
        User user = new User();
        user.setName("Власов Александр Сергеевич");
        user.setEmail("velesov7493@yandex.ru");
        user.setPassword("masterkey");
        store.save(user);
        int id = user.getId();
        User result = store.getById(id);
        assertEquals(user.getName(), result.getName());
    }

    @Test
    public void whenUpdateUser() {
        UserStore store = PsqlUserStore.getInstance();
        User user = new User();
        user.setName("Андреева Мария Александровна");
        user.setEmail("mail0001@yandex.ru");
        user.setPassword("masterkey");
        store.save(user);
        int id = user.getId();
        user = store.getById(id);
        user.setName("Андреева Мария");
        store.save(user);
        user = store.getById(id);
        assertEquals("Андреева Мария", user.getName());
    }

    @Test
    public void whenDeleteUser() {
        UserStore store = PsqlUserStore.getInstance();
        User user = new User();
        user.setName("Румянцева Мария Сергеевна");
        user.setEmail("mail0002@yandex.ru");
        user.setPassword("masterkey");
        store.save(user);
        int id = user.getId();
        store.delete(id);
        user = store.getById(id);
        assertNull(user);
    }
}