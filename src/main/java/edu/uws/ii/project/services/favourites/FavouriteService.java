package edu.uws.ii.project.services.favourites;

import edu.uws.ii.project.Repositories.FavouriteRepository;
import edu.uws.ii.project.domain.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavouriteService implements IFavouriteService {

    private final FavouriteRepository favouriteRepository;

    @Autowired
    public FavouriteService(FavouriteRepository favouriteRepository) {
        this.favouriteRepository = favouriteRepository;
    }

    @Override
    public Long getFavouriteCountByRecipe(Recipe recipe) {
        return favouriteRepository.countFavouritesByRecipeFav(recipe);
    }
}
