package edu.uws.ii.project.services.recipe_history;

import edu.uws.ii.project.domain.Recipe;

public interface IRecipeHistoryService {
    Long getDoneCountByRecipe(Recipe recipe);

    boolean isDone(Recipe recipe);

    void add(Long recipeId);

    void delete(Long recipeId);

    void deleteByRecipeId(Long id);
}
