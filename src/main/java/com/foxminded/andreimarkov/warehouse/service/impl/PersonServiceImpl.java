package com.foxminded.andreimarkov.warehouse.service.impl;

import com.foxminded.andreimarkov.warehouse.dao.impl.JdbcPersonDAOImpl;
import com.foxminded.andreimarkov.warehouse.dto.PersonDTO;
import com.foxminded.andreimarkov.warehouse.exceptions.ServiceException;
import com.foxminded.andreimarkov.warehouse.model.Person;
import com.foxminded.andreimarkov.warehouse.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
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
        log.debug("start method save personDTO");
        Person person = mapper.map(personDTO, Person.class);
        log.debug("saved person id {}", person.getId());
        return mapper.map(personDAO.save(person), PersonDTO.class);
    }

    @Override
    public List<PersonDTO> findAll() {
        log.debug("getting findAll in personService");
        List<Person> all = personDAO.findAll();
        if (all.isEmpty()) {
            log.warn("there are no any person");
            throw new ServiceException("Person is empty");
        }
        return mapListOfEntityToDTO(all);
    }

    @Override
    public Optional<PersonDTO> getById(long id) {
        log.debug("getting person by id {}", id);
        Optional<Person> optionalPerson = personDAO.getById(id);
        log.debug("get optional value");
        Person personById;
        if (!optionalPerson.isPresent()) {
            log.warn("no person with id {}",id);
            throw new ServiceException("No person with this id");
        }
        personById = optionalPerson.get();
        PersonDTO personDTO = mapper.map(personById, PersonDTO.class);
        log.debug("get personDTO");
        return Optional.ofNullable(personDTO);
    }

    @Override
    public PersonDTO update(PersonDTO personDTO) {
        log.debug("starting update personDTO");
        Person updated = personDAO.update(mapper.map(personDTO, Person.class));
        log.debug("updated personDTO");
        return mapper.map(updated, PersonDTO.class);
    }

    @Override
    public int delete(long id) {
        log.debug("starting delete person with id {}",id);
        return personDAO.delete(id);
    }

    private List<PersonDTO> mapListOfEntityToDTO(List<Person> all) {
        log.debug("start mapping List<Person> in List<PersonDTO>");
        return all.stream().map(person -> mapper.map(person,PersonDTO.class))
                .collect(Collectors.toList());
    }
}
