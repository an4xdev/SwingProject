package edu.uws.ii.project.services.profile;

public interface IProfileService {
    int getCountOfUserRecipes(Long userId);

    int getCountOfUserFavouritesRecipes(Long userId);

    int getCountOfUserDoneRecipes(Long userId);
}
