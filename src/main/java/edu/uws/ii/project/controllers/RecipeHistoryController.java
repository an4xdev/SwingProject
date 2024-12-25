package edu.uws.ii.project.controllers;

import edu.uws.ii.project.services.recipe_history.IRecipeHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/history")
public class RecipeHistoryController {

    private final IRecipeHistoryService recipeHistoryService;

    @Autowired
    public RecipeHistoryController(IRecipeHistoryService recipeHistoryService) {
        this.recipeHistoryService = recipeHistoryService;
    }

    @PostMapping("/add")
    public String add(@RequestParam Long recipeId) {
        recipeHistoryService.add(recipeId);
        return "redirect:/recipes/" + recipeId;
    }

    @PostMapping("/remove")
    public String remove(@RequestParam Long recipeId) {
        recipeHistoryService.delete(recipeId);
        return "redirect:/recipes/" + recipeId;
    }
}
