package edu.uws.ii.project.services.recipes;

import edu.uws.ii.project.domain.Recipe;
import edu.uws.ii.project.dtos.SearchFormDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    void save(Recipe recipe);
}
