package edu.uws.ii.project.services.ingredients;

import edu.uws.ii.project.domain.Ingredient;

import java.util.List;

public interface IIngredientsService {
    List<Ingredient> findAll();

    Ingredient save(Ingredient ingredient);
}
