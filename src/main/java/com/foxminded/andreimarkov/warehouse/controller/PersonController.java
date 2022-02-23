package com.foxminded.andreimarkov.warehouse.controller;

import com.foxminded.andreimarkov.warehouse.dto.PersonDTO;
import com.foxminded.andreimarkov.warehouse.exceptions.ServiceException;
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

    @GetMapping({"/get-all", "/get-all.html"})
    public String listPersons(Model model) {
        log.debug("start get-all persons");
        try {
            model.addAttribute("persons", personService.findAll());
        } catch (ServiceException e) {
            log.info("no one person in database");
        }
        log.info("start personService.findAll");
        return "persons/get-all";
    }

    @GetMapping("/add-person")
    public String newPerson(@ModelAttribute("person") PersonDTO person) {
        return "persons/add-person";
    }

    @PostMapping("/add-person")
    public String addPerson(PersonDTO person, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.error("error in post mapping add-person: " + result.toString());
            return "persons/add-person";
        }
        log.debug("no error in post mapping add-person");
        personService.save(person);
        log.info("performed the method personService.save");
        return "redirect:/persons/index";
    }

    @GetMapping("/view/{id}")
    public String showPersonById(@PathVariable String id, Model model) {
        log.debug("Getting view for person id: " + id);
        model.addAttribute("person", personService.getById(Long.valueOf(id)));
        log.info("add to model person by id: " + id);
        return "persons/view";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        log.debug("start to edit personDTO");
        PersonDTO personDTO = personService.getById(id).get();
        log.info("edit personDTO with id: " + personDTO.getId() + " name: " + personDTO.getFirstName() + " " + personDTO.getSurName());
        model.addAttribute("personDTO", personDTO);
        return "persons/update-person";
    }

    @PostMapping("/update-person/{id}")
    public String updatePerson(@PathVariable("id") long id, @ModelAttribute("person") PersonDTO personDTO,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.error("error in post mapping update-person: " + result.toString());
            return "persons/update-person";
        }
        log.debug("no error in post mapping update-person with id: " + id);
        personDTO.setId(id);
        personService.update(personDTO);
        log.info("performed the method personService.update person with id: " + id);
        return "redirect:/persons/index";
    }

    @GetMapping("/delete/{id}")
    public String deletePersonById(@PathVariable("id") long id, @ModelAttribute("person") PersonDTO personDTO) {
        log.debug("start deleting person");
        personService.delete(id);
        log.info("complete to delete person with id: " + id);
        return "redirect:/persons/index";
    }
}
