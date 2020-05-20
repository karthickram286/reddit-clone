package com.karthickram.redditclone.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String getHome(Model model) {
        model.addAttribute("title", "Hello from Thymeleaf");
        return "home";
    }
}
