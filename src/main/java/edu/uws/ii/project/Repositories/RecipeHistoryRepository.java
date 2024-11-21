package edu.uws.ii.project.Repositories;

import edu.uws.ii.project.domain.RecipeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeHistoryRepository extends JpaRepository<RecipeHistory, Long> {
}
