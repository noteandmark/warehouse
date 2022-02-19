package com.foxminded.andreimarkov.warehouse.controller;

import com.foxminded.andreimarkov.warehouse.dto.PersonDTO;
import com.foxminded.andreimarkov.warehouse.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/persons")
@Controller
@Slf4j
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping({"", "/index", "/index.html"})
    public String pagePersons() {
        return "persons/index";
    }

    @GetMapping({"/get-all","/get-all.html"})
    public String listPersons(Model model) {
        model.addAttribute("persons", personService.findAll());
        return "persons/get-all";
    }

    @GetMapping("/add-person")
    public String newPerson(@ModelAttribute("person") PersonDTO person) {
        return "persons/add-person";
    }

    @PostMapping("/add-person")
    public String addUser(PersonDTO person, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "persons/add-person";
        }
        personService.save(person);
        return "redirect:/persons/index";
    }

    @GetMapping("/view/{id}")
    public String showPersonById(@PathVariable String id, Model model) {
        model.addAttribute("person", personService.getById(Long.valueOf(id)));
        return "persons/view";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        log.debug("start method getById personDTO");
        PersonDTO personDTO = personService.getById(id).get();
        log.info("personDTO = " + personDTO.toString());
        model.addAttribute("personDTO", personDTO);
        return "persons/update-person";
    }

    @PostMapping("/update-person/{id}")
    public String updatePerson(@PathVariable("id") long id, @ModelAttribute("person") PersonDTO personDTO,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "persons/update-person";
        }
        personDTO.setId(id);
        personService.save(personDTO);
        return "redirect:/persons/index";
    }

    @GetMapping("/delete/{id}")
    public String deletePerson(@PathVariable("id") long id, @ModelAttribute("person") PersonDTO personDTO) {
        personService.delete(id);
        return "redirect:/persons/index";
    }
}
