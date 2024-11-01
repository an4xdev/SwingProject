package edu.uws.ii.project.controllers;

import edu.uws.ii.project.dtos.NavigationDTO;
import edu.uws.ii.project.services.NavigationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {
    @Autowired
    private NavigationService navigationService;

    @ModelAttribute("navigationData")
    public NavigationDTO populateNavigationData() {
        return navigationService.getNavigationData();
    }
}
