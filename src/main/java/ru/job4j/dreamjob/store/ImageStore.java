package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Image;

import java.io.InputStream;

public interface ImageStore {

    Image load(int id);

    void saveFromStream(int id, InputStream stream);

    boolean delete(int id);
}
