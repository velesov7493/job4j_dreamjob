package ru.job4j.dreamjob.store;

import org.junit.Test;
import ru.job4j.dreamjob.model.User;

import static org.junit.Assert.*;

public class PsqlUserStoreTest {

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