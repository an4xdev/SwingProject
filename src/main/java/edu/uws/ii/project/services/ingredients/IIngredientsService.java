package edu.uws.ii.project.services.ingredients;

import edu.uws.ii.project.domain.Ingredient;
import edu.uws.ii.project.domain.Recipe;

import java.util.List;

public interface IIngredientsService {
    List<Ingredient> findAll();
    List<Ingredient> findAllByRecipe(Recipe recipe);

    Ingredient save(Ingredient ingredient);
}
