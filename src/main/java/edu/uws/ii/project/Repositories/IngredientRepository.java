package edu.uws.ii.project.Repositories;

import edu.uws.ii.project.domain.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

}
