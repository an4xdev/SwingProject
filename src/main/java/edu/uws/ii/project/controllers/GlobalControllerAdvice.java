package edu.uws.ii.project.controllers;

import edu.uws.ii.project.dtos.NavigationDTO;
import edu.uws.ii.project.services.NavigationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

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
}
