package edu.uws.ii.project.services.favourites;

import edu.uws.ii.project.domain.Recipe;

public interface IFavouriteService {
    Long getFavouriteCountByRecipe(Recipe recipe);
}
