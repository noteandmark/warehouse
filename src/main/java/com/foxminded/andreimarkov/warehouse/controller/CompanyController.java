package com.foxminded.andreimarkov.warehouse.controller;

import com.foxminded.andreimarkov.warehouse.dto.CompanyDTO;
import com.foxminded.andreimarkov.warehouse.exceptions.ServiceException;
import com.foxminded.andreimarkov.warehouse.service.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/companies")
@Controller
@Slf4j
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping({"", "/index", "/index.html"})
    public String pageCompanies() {
        return "companies/index";
    }

    @GetMapping({"/get-all", "/get-all.html"})
    public String listCompanies(Model model) {
        log.debug("start get-all companies");
        try {
            model.addAttribute("companies", companyService.findAll());
        } catch (ServiceException e) {
            log.info("no one company in database");
        }
        log.info("start companyService.findAll");
        return "companies/get-all";
    }

    @GetMapping("/add-company")
    public String newCompany(@ModelAttribute("company") CompanyDTO companyDTO) {
        return "companies/add-company";
    }

    @PostMapping("/add-company")
    public String addCompany(CompanyDTO company, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.error("error in post mapping add-company: " + result.toString());
            return "companies/add-company";
        }
        log.debug("no error in post mapping add-company");
        companyService.save(company);
        log.info("performed the method companyService.save");
        return "redirect:/companies/index";
    }

    @GetMapping("/view/{id}")
    public String showCompanyById(@PathVariable String id, Model model) {
        log.debug("Getting view for company id: " + id);
        model.addAttribute("company", companyService.getById(Long.valueOf(id)));
        log.info("add to model company by id: " + id);
        return "companies/view";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        log.debug("start to edit companyDTO");
        CompanyDTO companyDTO = companyService.getById(id).get();
        log.info("edit companyDTO with id: " + companyDTO.getId() + " name: " + companyDTO.getName());
        model.addAttribute("companyDTO", companyDTO);
        return "companies/update-company";
    }

    @PostMapping("/update-company/{id}")
    public String updateCompany(@PathVariable("id") long id, @ModelAttribute("company") CompanyDTO companyDTO,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.error("error in post mapping update-company: " + result.toString());
            return "companies/update-company";
        }
        log.debug("no error in post mapping update-company with id: " + id);
        companyDTO.setId(id);
        companyService.update(companyDTO);
        log.info("performed the method companyService.update company with id: " + id);
        return "redirect:/companies/index";
    }

    @GetMapping("/delete/{id}")
    public String deleteCompanyById(@PathVariable("id") long id, @ModelAttribute("person") CompanyDTO companyDTO) {
        log.debug("start deleting company");
        companyService.delete(id);
        log.info("complete to delete company with id: " + id);
        return "redirect:/companies/index";
    }
}
