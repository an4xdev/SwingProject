package edu.uws.ii.project.Repositories;

import edu.uws.ii.project.domain.Category;
import edu.uws.ii.project.domain.Difficulty;
import edu.uws.ii.project.domain.Event;
import edu.uws.ii.project.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Set;

public interface RecipeRepository extends PagingAndSortingRepository<Recipe, Long>, JpaRepository<Recipe, Long> {

    List<Recipe> findAllByCategory(Category category);

    List<Recipe> findAllByDifficulty(Difficulty diff);

    List<Recipe> findAllByEvents(Set<Event> event);

    List<Recipe> findAllByUserId(Long userId);
}
