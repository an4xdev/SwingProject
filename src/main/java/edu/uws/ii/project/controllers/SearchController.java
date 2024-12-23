package edu.uws.ii.project.controllers;

import edu.uws.ii.project.domain.Category;
import edu.uws.ii.project.domain.Difficulty;
import edu.uws.ii.project.domain.Event;
import edu.uws.ii.project.domain.Recipe;
import edu.uws.ii.project.dtos.SearchFormDTO;
import edu.uws.ii.project.services.categories.ICategoryService;
import edu.uws.ii.project.services.difficulties.IDifficultyService;
import edu.uws.ii.project.services.events.IEventService;
import edu.uws.ii.project.services.recipes.IRecipeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController {

    private final ICategoryService categoryService;
    private final IDifficultyService difficultyService;
    private final IEventService eventService;
    private final IRecipeService recipeService;

    @Autowired
    public SearchController(ICategoryService categoryService, IDifficultyService difficultyService, IEventService eventService, IRecipeService recipeService) {
        this.categoryService = categoryService;
        this.difficultyService = difficultyService;
        this.eventService = eventService;
        this.recipeService = recipeService;
    }

    @ModelAttribute("categories")
    public List<Category> getCategories() {
        return categoryService.findAll();
    }

    @ModelAttribute("difficulties")
    public List<Difficulty> getDifficulties() {
        return difficultyService.findAll();
    }

    @ModelAttribute("events")
    public List<Event> getEvents() {
        return eventService.findAll();
    }

    @ModelAttribute("eventIds")
    public Long[] getEventIds() {
        return eventService.findAll().stream().map(Event::getId).toArray(Long[]::new);
    }

    @GetMapping
    public String search(Model model) {
        model.addAttribute("searchForm", new SearchFormDTO());
        model.addAttribute("showResults", false);
        model.addAttribute("recipes", new ArrayList<Recipe>());
        return "search";
    }

    @PostMapping("/results")
    public String searchResults(Model model, @Valid @ModelAttribute("searchForm") SearchFormDTO searchFormDTO, BindingResult result) {
        model.addAttribute("searchForm", searchFormDTO);

        if (result.hasErrors()) {
            return "search";
        }

        var recipeList = recipeService.findBySpecification(searchFormDTO);

        model.addAttribute("showResults", true);
        model.addAttribute("recipes", recipeList);

        return "search";
    }
}
