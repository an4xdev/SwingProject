package edu.uws.ii.project.Repositories;

import edu.uws.ii.project.domain.Rating;
import edu.uws.ii.project.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    List<Rating> findAllByRecipe(Recipe recipe);
}
