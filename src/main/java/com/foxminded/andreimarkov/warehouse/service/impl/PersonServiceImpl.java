package com.foxminded.andreimarkov.warehouse.service.impl;

import com.foxminded.andreimarkov.warehouse.dao.impl.JdbcPersonDAOImpl;
import com.foxminded.andreimarkov.warehouse.dto.PersonDTO;
import com.foxminded.andreimarkov.warehouse.model.Person;
import com.foxminded.andreimarkov.warehouse.service.PersonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonServiceImpl implements PersonService {

    private final JdbcPersonDAOImpl personDAO;
    private final ModelMapper mapper;

    @Autowired
    public PersonServiceImpl(JdbcPersonDAOImpl personDAO, ModelMapper modelMapper) {
        this.personDAO = personDAO;
        this.mapper = modelMapper;
    }

    @Override
    public PersonDTO save(PersonDTO personDTO) {
        Person person = mapper.map(personDTO, Person.class);
        return mapper.map(personDAO.save(person), PersonDTO.class);
    }

    @Override
    public List<PersonDTO> findAll() {
        List<Person> all = personDAO.findAll();
        return mapListOfEntityToDTO(all);
    }

    @Override
    public Optional<PersonDTO> getById(long id) {
        Person byId = personDAO.getById(id).get();
        return Optional.ofNullable(mapper.map(byId, PersonDTO.class));
    }

    @Override
    public PersonDTO update(PersonDTO personDTO) {
        Person updated = personDAO.update(mapper.map(personDTO, Person.class));
        return mapper.map(updated, PersonDTO.class);
    }

    @Override
    public int delete(long id) {
        return personDAO.delete(id);
    }

    private List<PersonDTO> mapListOfEntityToDTO(List<Person> all) {
        List<PersonDTO> personList = new ArrayList<>();
        for (Person person : all) {
            personList.add(mapper.map(person, PersonDTO.class));
        }
        return personList;
    }
}
