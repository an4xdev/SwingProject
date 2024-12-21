package edu.uws.ii.project.services.recipes;

import edu.uws.ii.project.domain.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IRecipeService {
    Optional<Recipe> getRecipeById(Long id);

    Page<Recipe> getPage(Pageable pageable);

    void deleteRecipeById(Long id);

    List<Recipe> findByCategory(String category);

    List<Recipe> findByDifficulty(String difficulty);

    List<Recipe> findByEvent(String event);
}
