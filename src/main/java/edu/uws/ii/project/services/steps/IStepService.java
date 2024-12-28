package edu.uws.ii.project.services.steps;

import edu.uws.ii.project.domain.Step;
import jakarta.validation.constraints.Size;

import java.util.List;

public interface IStepService {
    List<Step> findAllByRecipeId(Long recipeId);

    void saveAll(@Size List<Step> steps);

    void delete(Step step);

    void deleteAllByRecipeId(Long id);
}
