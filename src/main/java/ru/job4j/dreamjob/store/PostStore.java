package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Post;

import java.util.Collection;

public interface PostStore {

    Collection<Post> findAll();

    Collection<Post> findAllCreatedToday();

    Post getById(int id);

    void save(Post value);

    void delete(int id);
}
