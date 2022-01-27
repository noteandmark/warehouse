package com.foxminded.andreimarkov.warehouse.service.impl;

import com.foxminded.andreimarkov.warehouse.dao.impl.JdbcPersonDAOImpl;
import com.foxminded.andreimarkov.warehouse.dto.PersonDTO;
import com.foxminded.andreimarkov.warehouse.model.Person;
import com.foxminded.andreimarkov.warehouse.service.PersonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
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
        Person personById = personDAO.getById(id).get();
        PersonDTO personDTO = mapper.map(personById, PersonDTO.class);
        return Optional.ofNullable(personDTO);
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
        return all.stream().map(person -> mapper.map(person,PersonDTO.class))
                .collect(Collectors.toList());
    }
}