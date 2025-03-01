package edu.uws.ii.project.services.rating;

import edu.uws.ii.project.domain.Rating;
import edu.uws.ii.project.domain.Recipe;

public interface IRatingService {

    Float calculateRatingByRecipe(Recipe recipe);

    void rate(Long recipeId, Float rating);
    
    Float getRatingByRecipe(Recipe recipe);

    void delete(Rating rating);

    void deleteByRecipeId(Long id);
}
