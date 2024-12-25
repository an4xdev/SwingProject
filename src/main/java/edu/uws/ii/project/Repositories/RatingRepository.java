package edu.uws.ii.project.Repositories;

import edu.uws.ii.project.domain.Rating;
import edu.uws.ii.project.domain.Recipe;
import edu.uws.ii.project.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    List<Rating> findAllByRecipe(Recipe recipe);

    Optional<Rating> findByRecipeAndUser(Recipe recipe, User user);
}
