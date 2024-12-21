package edu.uws.ii.project.services.steps;

import edu.uws.ii.project.domain.Step;

import java.util.List;

public interface IStepService {
    List<Step> findAllByRecipeId(Long recipeId);
}
