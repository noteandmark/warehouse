package com.foxminded.andreimarkov.warehouse.service;

import com.foxminded.andreimarkov.warehouse.dto.PersonDTO;

import java.util.List;
import java.util.Optional;

public interface AbstractService<T> {

    T save(PersonDTO t);

    List<T> findAll();

    Optional<T> getById(long id);

    T update(T t);

    int delete(long id);
}
