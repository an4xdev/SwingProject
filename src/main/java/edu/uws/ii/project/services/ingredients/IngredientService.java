package edu.uws.ii.project.services.ingredients;

import edu.uws.ii.project.Repositories.IngredientRepository;
import edu.uws.ii.project.domain.Ingredient;
import org.springframework.stereotype.Service;

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
    public Ingredient save(Ingredient ingredient) {
        return ingredientRepository.saveAndFlush(ingredient);
    }
}
