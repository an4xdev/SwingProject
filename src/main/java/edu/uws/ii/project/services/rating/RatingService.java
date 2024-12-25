package edu.uws.ii.project.services.rating;

import edu.uws.ii.project.Repositories.RatingRepository;
import edu.uws.ii.project.domain.Rating;
import edu.uws.ii.project.domain.Recipe;
import edu.uws.ii.project.services.recipes.IRecipeService;
import edu.uws.ii.project.services.user.IUserService;
import org.springframework.stereotype.Service;

@Service
public class RatingService implements IRatingService {

    private final RatingRepository ratingRepository;
    private final IRecipeService recipeService;
    private final IUserService userService;

    public RatingService(RatingRepository ratingRepository, IRecipeService recipeService, IUserService userService) {
        this.ratingRepository = ratingRepository;
        this.recipeService = recipeService;
        this.userService = userService;
    }


    @Override
    public Float calculateRatingByRecipe(Recipe recipe) {
        var ratings = ratingRepository.findAllByRecipe(recipe);
        var sum = ratings.stream().map(Rating::getRating).reduce(Float::sum).orElse(0.0f);

        if (!ratings.isEmpty()) {
            return sum / ratings.size();
        }
        return 0.0f;
    }

    @Override
    public void rate(Long recipeId, Float rating) {
        var user = userService.getCurrentUser();
        var recipe = recipeService.findById(recipeId);
        var existingRating = ratingRepository.findByRecipeAndUser(recipe.get(), user);
        if(existingRating.isPresent()) {
            existingRating.get().setRating(rating);
            ratingRepository.save(existingRating.get());
        } else {
            Rating newRating = new Rating(user, recipe.get(), rating);
            ratingRepository.save(newRating);
        }
    }

    @Override
    public Float getRatingByRecipe(Recipe recipe) {
        var rating = ratingRepository.findByRecipeAndUser(recipe, userService.getCurrentUser());
        return rating.map(Rating::getRating).orElse(0.0F);
    }
}
