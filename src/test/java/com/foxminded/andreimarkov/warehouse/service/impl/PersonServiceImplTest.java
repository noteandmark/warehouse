package com.foxminded.andreimarkov.warehouse.service.impl;

import com.foxminded.andreimarkov.warehouse.dao.impl.JdbcPersonDAOImpl;
import com.foxminded.andreimarkov.warehouse.dto.PersonDTO;
import com.foxminded.andreimarkov.warehouse.mapper.PersonMapper;
import com.foxminded.andreimarkov.warehouse.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class PersonServiceImplTest {

    @Mock
    private JdbcPersonDAOImpl personDAO;

    @Mock
    private PersonMapper mapper;
    private PersonDTO personDTO = new PersonDTO();
    private Person person = new Person();

    @InjectMocks
    private PersonServiceImpl personService;

    @BeforeEach
    void setUp() {
        when(mapper.toEntity(any())).thenReturn(person);
        when(mapper.toDto(any())).thenReturn(personDTO);
        personDTO.setId(1L);
        personDTO.setFirstName("First");
        personDTO.setSurName("Last");
        personDTO.setAddress("some street");
        personDTO.setPhone("111-22-33");
        personDTO.setBalance(0);
    }

    @Test
    void save() {
        personService.save(personDTO);
        verify(personDAO, only()).save(any(Person.class));
    }

    @Test
    void findAll() {
        personService.findAll();
        verify(personDAO, only()).findAll();
    }

    @Test
    void getById() {
        person.setId(1L);
        person.setFirstName("First");
        person.setSurName("Last");
        person.setAddress("some street");
        person.setPhone("111-22-33");
        person.setBalance(0);
        when(personDAO.getById(anyLong())).thenReturn(Optional.of(person));
        Optional<PersonDTO> actual = personService.getById(1L);
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
        person.setId(1L);
        when(personDAO.getById(anyLong())).thenReturn(Optional.of(person));
        personService.delete(personDTO.getId());
        verify(personDAO,times(1)).delete(person.getId());
    }
}