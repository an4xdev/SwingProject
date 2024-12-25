package edu.uws.ii.project.services.favourites;

import edu.uws.ii.project.Repositories.FavouriteRepository;
import edu.uws.ii.project.domain.Favourite;
import edu.uws.ii.project.domain.Recipe;
import edu.uws.ii.project.services.recipes.IRecipeService;
import edu.uws.ii.project.services.user.IUserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavouriteService implements IFavouriteService {

    private final FavouriteRepository favouriteRepository;
    private final IUserService userService;
    private final IRecipeService recipeService;

    @Autowired
    public FavouriteService(FavouriteRepository favouriteRepository, IUserService userService, IRecipeService recipeService) {
        this.favouriteRepository = favouriteRepository;
        this.userService = userService;
        this.recipeService = recipeService;
    }

    @Override
    public Long getFavouriteCountByRecipe(Recipe recipe) {
        return favouriteRepository.countFavouritesByRecipe(recipe);
    }

    @Override
    public boolean isFavourite(Recipe recipe) {
        return favouriteRepository.existsByRecipeAndUser(recipe, userService.getCurrentUser());
    }

    @Override
    public void add(Long recipeId) {
        var user = userService.getCurrentUser();
        var recipe = recipeService.findById(recipeId);
        Favourite favourite = new Favourite(user, recipe.get());
        favouriteRepository.save(favourite);
    }

    @Override
    @Transactional
    public void remove(Long recipeId) {
        var user = userService.getCurrentUser();
        var recipe = recipeService.findById(recipeId);
        favouriteRepository.deleteByRecipeAndUser(recipe.get(), user);
    }
}
