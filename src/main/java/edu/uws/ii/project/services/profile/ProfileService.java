package edu.uws.ii.project.services.profile;

import edu.uws.ii.project.Repositories.FavouriteRepository;
import edu.uws.ii.project.Repositories.RecipeHistoryRepository;
import edu.uws.ii.project.Repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService implements IProfileService {

    private final RecipeRepository recipeRepository;
    private final FavouriteRepository favouriteRepository;
    private final RecipeHistoryRepository recipeHistoryRepository;

    @Autowired
    public ProfileService(RecipeRepository recipeRepository, FavouriteRepository favouriteRepository, RecipeHistoryRepository recipeHistoryRepository) {
        this.recipeRepository = recipeRepository;
        this.favouriteRepository = favouriteRepository;
        this.recipeHistoryRepository = recipeHistoryRepository;
    }

    @Override
    public int getCountOfUserRecipes(Long userId) {
        var recipes = recipeRepository.findAllByUserId(userId);
        return recipes.size();
    }

    @Override
    public int getCountOfUserFavouritesRecipes(Long userId) {
        var favourites = favouriteRepository.findAllByUserId(userId);
        return favourites.size();
    }

    @Override
    public int getCountOfUserDoneRecipes(Long userId) {
        var doneRecipes = recipeHistoryRepository.findAllByUserId(userId);
        return doneRecipes.size();
    }
}
