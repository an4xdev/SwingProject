package edu.uws.ii.project.services.recipe_history;

import edu.uws.ii.project.Repositories.RecipeHistoryRepository;
import edu.uws.ii.project.domain.Recipe;
import edu.uws.ii.project.domain.RecipeHistory;
import edu.uws.ii.project.services.recipes.IRecipeService;
import edu.uws.ii.project.services.user.IUserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipeHistoryService implements IRecipeHistoryService {

    private final RecipeHistoryRepository recipeHistoryRepository;
    private final IUserService userService;
    private final IRecipeService recipeService;

    @Autowired
    public RecipeHistoryService(RecipeHistoryRepository recipeHistoryRepository, IUserService userService, IRecipeService recipeService) {
        this.recipeHistoryRepository = recipeHistoryRepository;
        this.userService = userService;
        this.recipeService = recipeService;
    }

    @Override
    public Long getDoneCountByRecipe(Recipe recipe) {
        return recipeHistoryRepository.countRecipeHistoriesByRecipe(recipe);
    }

    @Override
    public boolean isDone(Recipe recipe) {
        return recipeHistoryRepository.existsByRecipeAndUser(recipe, userService.getCurrentUser());
    }

    @Override
    public void add(Long recipeId) {
        var user = userService.getCurrentUser();
        var recipe = recipeService.findById(recipeId);
        RecipeHistory recipeHistory = new RecipeHistory(user, recipe.get());
        recipeHistoryRepository.save(recipeHistory);
    }

    @Override
    @Transactional
    public void delete(Long recipeId) {
        var user = userService.getCurrentUser();
        var recipe = recipeService.findById(recipeId);
        recipeHistoryRepository.deleteByRecipeAndUser(recipe.get(), user);
    }
}
