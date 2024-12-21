package edu.uws.ii.project.Repositories;

import edu.uws.ii.project.domain.Ingredient;
import edu.uws.ii.project.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    List<Ingredient> findAllByRecipes(Set<Recipe> recipes);
}
