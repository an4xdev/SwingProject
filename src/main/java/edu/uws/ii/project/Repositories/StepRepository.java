package edu.uws.ii.project.Repositories;

import edu.uws.ii.project.domain.Step;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StepRepository extends JpaRepository<Step, Long> {
    List<Step> findAllByRecipeId(Long recipeId);
}
