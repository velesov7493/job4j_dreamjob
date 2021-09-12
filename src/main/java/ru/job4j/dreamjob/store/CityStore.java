package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.City;

import java.util.Collection;

public interface CityStore {

    Collection<City> findAll();

    City getById(int id);

    void save(City value);

    void delete(int id);
}
