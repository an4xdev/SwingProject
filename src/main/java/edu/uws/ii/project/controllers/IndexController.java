package edu.uws.ii.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/details")
    public String details() {
        return "details";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/add_recipe")
    public String addRecipe() {
        return "add_recipe";
    }

    @GetMapping("/search")
    public String search() {
        return "search";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }
}
