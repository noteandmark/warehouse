package com.foxminded.andreimarkov.warehouse.service.impl;

import com.foxminded.andreimarkov.warehouse.dao.impl.JdbcPersonDAOImpl;
import com.foxminded.andreimarkov.warehouse.dto.PersonDTO;
import com.foxminded.andreimarkov.warehouse.mapper.PersonMapper;
import com.foxminded.andreimarkov.warehouse.model.Person;
import com.foxminded.andreimarkov.warehouse.service.PersonService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonServiceImpl implements PersonService {

    private final JdbcPersonDAOImpl personDAO;
    private final PersonMapper mapper;

    public PersonServiceImpl(JdbcPersonDAOImpl personDAO, PersonMapper mapper) {
        this.personDAO = personDAO;
        this.mapper = mapper;
    }

    @Override
    public PersonDTO save(PersonDTO personDTO) {
        Person person = mapper.toEntity(personDTO);
        return mapper.toDto(personDAO.save(person));
    }

    @Override
    public List<PersonDTO> findAll() {
        List<Person> all = personDAO.findAll();
        return mapListOfEntityToDTO(all);
    }

    @Override
    public Optional<PersonDTO> getById(long id) {
        Person byId = personDAO.getById(id).get();
        return Optional.ofNullable(mapper.toDto(byId));
    }

    @Override
    public PersonDTO update(PersonDTO personDTO) {
        Person updated = personDAO.update(mapper.toEntity(personDTO));
        return mapper.toDto(updated);
    }

    @Override
    public int delete(long id) {
        return personDAO.delete(id);
    }

    private List<PersonDTO> mapListOfEntityToDTO(List<Person> all) {
        List<PersonDTO> personList = new ArrayList<>();
        for (Person person : all) {
            personList.add(mapper.toDto(person));
        }
        return personList;
    }
}
