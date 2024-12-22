package edu.uws.ii.project.controllers;

import edu.uws.ii.project.domain.Event;
import edu.uws.ii.project.dtos.SearchFormDTO;
import edu.uws.ii.project.services.categories.ICategoryService;
import edu.uws.ii.project.services.difficulties.IDifficultyService;
import edu.uws.ii.project.services.events.IEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/search")
public class SearchController {

    private final ICategoryService categoryService;
    private final IDifficultyService difficultyService;
    private final IEventService eventService;

    @Autowired
    public SearchController(ICategoryService categoryService, IDifficultyService difficultyService, IEventService eventService) {
        this.categoryService = categoryService;
        this.difficultyService = difficultyService;
        this.eventService = eventService;
    }

    @GetMapping
    public String search(Model model) {
        model.addAttribute("searchForm", new SearchFormDTO());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("difficulties", difficultyService.findAll());
        model.addAttribute("events", eventService.findAll());
        model.addAttribute("eventIds", eventService.findAll().stream().map(Event::getId).toArray());
        return "search";
    }
}
