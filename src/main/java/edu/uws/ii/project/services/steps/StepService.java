package edu.uws.ii.project.services.steps;

import edu.uws.ii.project.Repositories.StepRepository;
import edu.uws.ii.project.domain.Step;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StepService implements IStepService {
    private final StepRepository stepRepository;

    @Autowired
    public StepService(StepRepository stepRepository) {
        this.stepRepository = stepRepository;
    }

    @Override
    public List<Step> findAllByRecipeId(Long recipeId) {
        return stepRepository.findAllByRecipeId(recipeId);
    }

    @Override
    public void saveAll(@Size List<Step> steps) {
        stepRepository.saveAll(steps);
    }
}
