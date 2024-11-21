package edu.uws.ii.project.Repositories;

import edu.uws.ii.project.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
