package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Post;

import java.util.*;
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
    public Collection<Post> findAllCreatedToday() {
        List<Post> result = new ArrayList<>();
        Calendar cl = Calendar.getInstance();
        Calendar clLeft = Calendar.getInstance();
        Calendar clRight = Calendar.getInstance();
        clLeft.set(cl.get(
                Calendar.YEAR), cl.get(Calendar.MONTH), cl.get(Calendar.DATE),
                0, 0, 0
        );
        clRight.set(cl.get(
                Calendar.YEAR), cl.get(Calendar.MONTH), cl.get(Calendar.DATE),
                23, 59, 59
        );
        for (Integer key : posts.keySet()) {
            Post value = posts.get(key);
            if (
                    value.getCreated().after(clLeft.getTime())
                    && value.getCreated().before(clRight.getTime())
            ) {
                result.add(value);
            }
        }
        return result;
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
