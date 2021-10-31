package ru.job4j.dreamjob.store;

import org.junit.Test;
import ru.job4j.dreamjob.model.Post;

import static org.junit.Assert.*;

public class PsqlPostStoreTest {

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