package edu.uws.ii.project.services.recipe_history;

import edu.uws.ii.project.Repositories.RecipeHistoryRepository;
import edu.uws.ii.project.domain.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipeHistoryService implements IRecipeHistoryService {

    private final RecipeHistoryRepository recipeHistoryRepository;

    @Autowired
    public RecipeHistoryService(RecipeHistoryRepository recipeHistoryRepository) {
        this.recipeHistoryRepository = recipeHistoryRepository;
    }

    @Override
    public Long getDoneCountByRecipe(Recipe recipe) {
        return recipeHistoryRepository.countRecipeHistoriesByRecipe(recipe);
    }
}
