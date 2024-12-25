package edu.uws.ii.project.services.rating;

import edu.uws.ii.project.domain.Recipe;

public interface IRatingService {

    Float calculateRatingByRecipe(Recipe recipe);
}
