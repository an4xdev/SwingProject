package edu.uws.ii.project.services.rating;

import edu.uws.ii.project.Repositories.RatingRepository;
import edu.uws.ii.project.domain.Rating;
import edu.uws.ii.project.domain.Recipe;
import org.springframework.stereotype.Service;

@Service
public class RatingService implements IRatingService {

    private final RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
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
}
