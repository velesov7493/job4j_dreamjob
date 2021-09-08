package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Post;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemPostStore implements PostStore {

    private static final MemPostStore INSTANCE = new MemPostStore();

    private final AtomicInteger generator;
    private final Map<Integer, Post> posts;

    private MemPostStore() {
        posts = new ConcurrentHashMap<>();
        generator = new AtomicInteger(0);
    }

    public static PostStore getInstance() {
        return INSTANCE;
    }

    @Override
    public Collection<Post> findAll() {
        return posts.values();
    }

    @Override
    public Post getById(int id) {
        return posts.get(id);
    }

    @Override
    public void save(Post value) {
        if (value.getId() == 0) {
            value.setId(generator.incrementAndGet());
        }
        posts.put(value.getId(), value);
    }

    @Override
    public void delete(int id) {
        posts.remove(id);
    }
}
