package edu.uws.ii.project.services.recipes;

import edu.uws.ii.project.domain.Recipe;
import edu.uws.ii.project.dtos.FormDTO;
import edu.uws.ii.project.dtos.SearchFormDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Optional;

public interface IRecipeService {
    Optional<Recipe> findById(Long id);

    Page<Recipe> getPage(Pageable pageable);

    void deleteById(Long id);

    List<Recipe> findByCategory(String category);

    List<Recipe> findByDifficulty(String difficulty);

    List<Recipe> findByEvent(String event);

    List<Recipe> findBySpecification(@Valid SearchFormDTO searchFormDTO);

    void save(FormDTO recipeForm, String photoPath);

    @PreAuthorize("#recipe.user.username == authentication.name or hasRole('ADMIN')")
    void update(FormDTO recipeForm, Recipe recipe, String photoPath);
}
