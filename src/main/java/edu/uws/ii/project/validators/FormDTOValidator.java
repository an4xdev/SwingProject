package edu.uws.ii.project.validators;

import edu.uws.ii.project.dtos.FormDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
public class FormDTOValidator {
    public void validateIngredients(FormDTO recipeForm, BindingResult bindingResult) {
        int existingIngredientsCount = recipeForm.getIngredients() == null ? 0 : recipeForm.getIngredients().size();
        int newIngredientsCount = recipeForm.getIngredientsAdded() == null ? 0 : recipeForm.getIngredientsAdded().size();

        if (existingIngredientsCount == 0 && newIngredientsCount == 0) {
            bindingResult.rejectValue("ingredients", "error.ingredients", "At least one ingredient is required.");
        }
    }
}
