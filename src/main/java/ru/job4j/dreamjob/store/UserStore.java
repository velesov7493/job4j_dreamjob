package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.User;

import java.util.Collection;

public interface UserStore {

    Collection<User> findAll();

    User getById(int id);

    User login(String email, String password);

    void save(User value);

    void delete(int id);
}
