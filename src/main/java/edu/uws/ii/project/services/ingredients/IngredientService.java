package edu.uws.ii.project.services.ingredients;

import edu.uws.ii.project.Repositories.IngredientRepository;
import edu.uws.ii.project.domain.Ingredient;
import edu.uws.ii.project.domain.Recipe;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class IngredientService implements IIngredientsService {
    private final IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public List<Ingredient> findAll() {
        return ingredientRepository.findAll();
    }

    @Override
    public List<Ingredient> findAllByRecipe(Recipe recipe) {
        return ingredientRepository.findAllByRecipes(new HashSet<>(List.of(recipe)));
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }
}
