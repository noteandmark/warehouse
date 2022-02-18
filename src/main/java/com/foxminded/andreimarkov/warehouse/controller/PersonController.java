package com.foxminded.andreimarkov.warehouse.controller;

import com.foxminded.andreimarkov.warehouse.dto.PersonDTO;
import com.foxminded.andreimarkov.warehouse.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.jws.WebParam;

@RequestMapping("/persons")
@Controller
@Slf4j
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping({"", "/index","/index.html"})
    public String pagePersons () {
        return "persons/index";
    }

    @GetMapping({"/get-all","get-all.html"})
    public String listPersons (Model model) {
        model.addAttribute("persons",personService.findAll());
        return "persons/get-all";
    }

    @GetMapping({"/add","/add.html"})
    public String showSignUpForm(PersonDTO personDTO) {
        return "persons/add-person";
    }

    @GetMapping({"/view/{id}","/view/{id}.html"})
    public String showPersonById(@PathVariable String id, Model model) {
        model.addAttribute("person",personService.getById(Long.valueOf(id)));
        return "persons/view";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        log.debug("start method getById personDTO");
        PersonDTO personDTO = personService.getById(id).get();
        log.info("personDTO = " + personDTO.toString());
        model.addAttribute("person", personDTO);
        return "persons/update-person";
    }

}
