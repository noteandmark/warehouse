package com.foxminded.andreimarkov.warehouse.dao;

import java.util.List;
import java.util.Optional;

public interface AbstractDAO<T> {

    T create(T t);

    Optional<T> getById(Long id);

    List<T> findAll();

    T update(T t);

    int delete(Long id);

}
