package com.foxminded.andreimarkov.warehouse.dao;

import java.util.List;
import java.util.Optional;

public interface AbstractDAO<T> {
    T create(T t);

    Optional<T> getById(int id);

    List<T> getAllPersons();

    T updatePerson(T t);

    void deletePerson(T t);




}
