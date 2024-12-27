package edu.uws.ii.project.services.favourites;

import edu.uws.ii.project.domain.Favourite;
import edu.uws.ii.project.domain.Recipe;

public interface IFavouriteService {
    Long getFavouriteCountByRecipe(Recipe recipe);
    boolean isFavourite(Recipe recipe);

    void add(Long recipeId);

    void remove(Long recipeId);

    void delete(Favourite favourite);

    void deleteByRecipeId(Long recipeId);
}
