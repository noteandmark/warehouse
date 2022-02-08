package com.foxminded.andreimarkov.warehouse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomepageController {

    @RequestMapping({"","/", "/index","/index.html"})
    public String menuPage() {
        return "index";
    }

}
