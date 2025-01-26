package edu.uws.ii.project.controllers;

import edu.uws.ii.project.dtos.NavigationDTO;
import edu.uws.ii.project.exceptions.*;
import edu.uws.ii.project.services.NavigationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDate;

@ControllerAdvice
public class GlobalControllerAdvice {
    private final NavigationService navigationService;

    @Autowired
    public GlobalControllerAdvice(NavigationService navigationService) {
        this.navigationService = navigationService;
    }

    @ModelAttribute("navigationData")
    public NavigationDTO populateNavigationData() {
        return navigationService.getNavigationData();
    }

    @ModelAttribute("today")
    public LocalDate populateToday() {
        return LocalDate.now();
    }

    private void addErrorAttributes(Model model, HttpServletRequest req, Exception ex) {
        model.addAttribute("exception", ex.getMessage());
        model.addAttribute("url", req.getRequestURL());
        model.addAttribute("method", req.getMethod());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RecipeNotFound.class)
    public String handleRecipeNotFound(Model model, HttpServletRequest req, Exception ex) {
        model.addAttribute("title", "Recipe");
        model.addAttribute("message", "Recipe not found");
        addErrorAttributes(model, req, ex);
        return "not_found";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CategoryNotFound.class)
    public String handleCategoryNotFound(Model model, HttpServletRequest req, Exception ex) {
        model.addAttribute("title", "Category");
        model.addAttribute("message", "Category not found");
        addErrorAttributes(model, req, ex);
        return "not_found";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DifficultyNotFound.class)
    public String handleDifficultyNotFound(Model model, HttpServletRequest req, Exception ex) {
        model.addAttribute("title", "Difficulty");
        model.addAttribute("message", "Difficulty not found");
        addErrorAttributes(model, req, ex);
        return "not_found";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EventNotFound.class)
    public String handleEventNotFound(Model model, HttpServletRequest req, Exception ex) {
        model.addAttribute("title", "Event");
        model.addAttribute("message", "Event not found");
        addErrorAttributes(model, req, ex);
        return "not_found";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFound.class)
    public String handleUserNotFound(Model model, HttpServletRequest req, Exception ex) {
        model.addAttribute("title", "User");
        model.addAttribute("message", "User not found");
        addErrorAttributes(model, req, ex);
        return "not_found";
    }
}
