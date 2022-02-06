package com.foxminded.andreimarkov.warehouse.service.impl;

import com.foxminded.andreimarkov.warehouse.dao.impl.JdbcPersonDAOImpl;
import com.foxminded.andreimarkov.warehouse.dto.PersonDTO;
import com.foxminded.andreimarkov.warehouse.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class PersonServiceImplTest {

    @Mock
    private JdbcPersonDAOImpl personDAO;
    private PersonDTO personDTO;
    private Person person;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private PersonServiceImpl personService;

    @BeforeEach
    void setUp() {
        personDTO = new PersonDTO();
        person  = new Person();
        when(mapper.map(personDTO,Person.class)).thenReturn(person);
        when(mapper.map(person,PersonDTO.class)).thenReturn(personDTO);
        personDTO.setId(10000L);
        personDTO.setFirstName("First");
        personDTO.setSurName("Last");
        personDTO.setAddress("some street");
        personDTO.setPhone("111-22-33");
        personDTO.setBalance(0);
    }

    @Test
    void save() {
        when(personDAO.save(any())).thenReturn(new Person());
        personService.save(personDTO);
        verify(personDAO, only()).save(any(Person.class));
    }

    @Test
    void findAll() {
        List<Person> all = new ArrayList<>();
        all.add(person);
        when(personDAO.findAll()).thenReturn(all);
        personService.findAll();
        verify(personDAO, only()).findAll();
    }

    @Test
    void getById() {
        person.setId(10000L);
        person.setFirstName("First");
        person.setSurName("Last");
        person.setAddress("some street");
        person.setPhone("111-22-33");
        person.setBalance(0);
        when(personDAO.getById(anyLong())).thenReturn(Optional.ofNullable(person));
        Optional<PersonDTO> actual = personService.getById(10000L);
        assertEquals(personDTO, actual.get());
    }

    @Test
    void update() {
        personDTO.setFirstName("New");
        personDTO.setSurName("Person");
        personService.update(personDTO);
        verify(personDAO,times(1)).update(person);
    }

    @Test
    void delete() {
        person.setId(10000L);
        when(personDAO.getById(anyLong())).thenReturn(Optional.of(person));
        personService.delete(personDTO.getId());
        verify(personDAO,times(1)).delete(person.getId());
    }
}