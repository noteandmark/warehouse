package com.foxminded.andreimarkov.warehouse.mapper;

import com.foxminded.andreimarkov.warehouse.dto.PersonDTO;
import com.foxminded.andreimarkov.warehouse.model.Person;
import org.springframework.beans.factory.annotation.Autowired;

public class PersonMapper extends AbstractMapper<Person, com.foxminded.andreimarkov.warehouse.dto.PersonDTO> {

    @Autowired
    public PersonMapper() {
        super(Person.class, PersonDTO.class);
    }

}
