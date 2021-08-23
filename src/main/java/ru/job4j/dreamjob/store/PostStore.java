package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Post;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PostStore {

    private static final PostStore INSTANCE = new PostStore();

    private final Map<Integer, Post> posts;

    private PostStore() {
        posts = new ConcurrentHashMap<>();
        posts.put(1, new Post(1, "Младший java-разработчик (junior)"));
        posts.put(2, new Post(2, "Обычный java-программист (middle)"));
        posts.put(3, new Post(3, "Старший java-программист (senior)"));
    }

    public static PostStore getInstance() {
        return INSTANCE;
    }

    public Collection<Post> findAll() {
        return posts.values();
    }
}
