package edu.uws.ii.project.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(Model model) {
        model.addAttribute("today", LocalDate.now());
        return "404";
    }

    @RequestMapping("/403")
    public String handle403(Model model) {
        model.addAttribute("today", LocalDate.now());
        return "403";
    }

    @RequestMapping("/404")
    public String handle404(Model model) {
        model.addAttribute("today", LocalDate.now());
        return "404";
    }

    @RequestMapping("/500")
    public String handle500(Model model) {
        model.addAttribute("today", LocalDate.now());
        return "500";
    }
}
