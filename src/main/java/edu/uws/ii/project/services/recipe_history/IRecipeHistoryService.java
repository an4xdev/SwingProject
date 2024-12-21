package edu.uws.ii.project.services.recipe_history;

import edu.uws.ii.project.domain.Recipe;

public interface IRecipeHistoryService {
    Long getDoneCountByRecipe(Recipe recipe);
}
