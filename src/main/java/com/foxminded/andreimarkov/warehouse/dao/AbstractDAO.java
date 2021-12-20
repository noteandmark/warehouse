package com.foxminded.andreimarkov.warehouse.dao;

import java.util.List;
import java.util.Optional;

public abstract class AbstractDAO<T> {
    protected abstract T create(T t);

    protected abstract Optional<T> getById(int id);

    protected abstract List<T> getAllPersons();

    protected abstract T updatePerson(T t);

    protected abstract void deletePerson(T t);




}
