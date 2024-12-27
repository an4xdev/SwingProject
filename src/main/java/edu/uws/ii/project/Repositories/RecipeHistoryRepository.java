package edu.uws.ii.project.Repositories;

import edu.uws.ii.project.domain.Recipe;
import edu.uws.ii.project.domain.RecipeHistory;
import edu.uws.ii.project.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeHistoryRepository extends JpaRepository<RecipeHistory, Long> {
    List<RecipeHistory> findAllByUserId(Long userId);

    Long countRecipeHistoriesByRecipe(Recipe recipe);

    boolean existsByRecipeAndUser(Recipe recipe, User currentUser);

    void deleteByRecipeAndUser(Recipe recipe, User user);

    void deleteByRecipe_Id(Long id);
}
