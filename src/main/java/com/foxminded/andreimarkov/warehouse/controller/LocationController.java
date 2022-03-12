package com.foxminded.andreimarkov.warehouse.controller;

import com.foxminded.andreimarkov.warehouse.dto.LocationDTO;
import com.foxminded.andreimarkov.warehouse.exceptions.ServiceException;
import com.foxminded.andreimarkov.warehouse.service.impl.LocationServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/locations")
@Controller
@Slf4j
public class LocationController {
    private final LocationServiceImpl locationService;

    @Autowired
    public LocationController(LocationServiceImpl locationService) {
        this.locationService = locationService;
    }

    @GetMapping({"", "/index", "/index.html"})
    public String pageLocations() {
        return "locations/index";
    }

    @GetMapping({"/get-all", "/get-all.html"})
    public String listLocations(Model model) {
        log.debug("start get-all locations");
        try {
            model.addAttribute("locations", locationService.findAll());
        } catch (ServiceException e) {
            log.info("no one location in database");
        }
        log.info("start locationService.findAll");
        return "locations/get-all";
    }

    @GetMapping("/add-location")
    public String newLocation(@ModelAttribute("location") LocationDTO locationDTO) {
        return "locations/add-location";
    }

    @PostMapping("/add-location")
    public String addLocation(LocationDTO location, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.error("error in post mapping add-location: " + result.toString());
            return "locations/add-location";
        }
        log.debug("no error in post mapping add-location");
        locationService.save(location);
        log.info("performed the method locationService.save");
        return "redirect:/locations/index";
    }

    @GetMapping("/view/{id}")
    public String showLocationById(@PathVariable String id, Model model) {
        log.debug("Getting view for location id: " + id);
        model.addAttribute("location", locationService.getById(Long.valueOf(id)));
        log.info("add to model location by id: " + id);
        return "locations/view";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        log.debug("start to edit locationDTO");
        LocationDTO locationDTO = locationService.getById(id).get();
        log.info("edit companyDTO with id: " + locationDTO.getId() + " name: " + locationDTO.getWarehouseName());
        model.addAttribute("locationDTO", locationDTO);
        return "locations/update-location";
    }

    @PostMapping("/update-location/{id}")
    public String updateLocation(@PathVariable("id") long id, @ModelAttribute("location") LocationDTO locationDTO,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.error("error in post mapping update-location: " + result.toString());
            return "locations/update-location";
        }
        log.debug("no error in post mapping update-location with id: " + id);
        locationDTO.setId(id);
        locationService.update(locationDTO);
        log.info("performed the method locationService.update location with id: " + id);
        return "redirect:/locations/index";
    }

    @GetMapping("/delete/{id}")
    public String deleteLocationById(@PathVariable("id") long id, @ModelAttribute("person") LocationDTO locationDTO) {
        log.debug("start deleting location");
        locationService.delete(id);
        log.info("complete to delete location with id: " + id);
        return "redirect:/locations/index";
    }
}
