package edu.uws.ii.project.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        model.addAttribute("today", LocalDate.now());

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == 403) {
                return "403";
            } else if (statusCode == 404) {
                return "404";
            } else if (statusCode == 500) {
                return "500";
            }
        }
        return "error";
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
