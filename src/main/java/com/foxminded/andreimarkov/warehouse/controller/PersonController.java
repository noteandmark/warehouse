package com.foxminded.andreimarkov.warehouse.controller;

import com.foxminded.andreimarkov.warehouse.dto.PersonDTO;
import com.foxminded.andreimarkov.warehouse.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/persons")
@Controller
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

    @GetMapping("/signup")
    public String showSignUpForm(PersonDTO personDTO) {
        return "persons/add-person";
    }
}
