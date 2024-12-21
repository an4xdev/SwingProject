package edu.uws.ii.project.controllers;

import edu.uws.ii.project.domain.Recipe;
import edu.uws.ii.project.services.recipes.IRecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    private final IRecipeService recipeService;

    @Autowired
    public IndexController(IRecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/")
    public String index(Model model) {
        Page<Recipe> recipes = recipeService.getPage(PageRequest.of(0, 6));
        model.addAttribute("recipes", recipes);
        return "index";
    }
}
