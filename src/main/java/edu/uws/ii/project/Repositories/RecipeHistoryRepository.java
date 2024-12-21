package edu.uws.ii.project.Repositories;

import edu.uws.ii.project.domain.Recipe;
import edu.uws.ii.project.domain.RecipeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeHistoryRepository extends JpaRepository<RecipeHistory, Long> {
    List<RecipeHistory> findAllByUserId(Long userId);

    Long countRecipeHistoriesByRecipe(Recipe recipe);
}
