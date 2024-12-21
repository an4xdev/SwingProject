package edu.uws.ii.project.services.recipes;

import edu.uws.ii.project.Repositories.CategoryRepository;
import edu.uws.ii.project.Repositories.DifficultyRepository;
import edu.uws.ii.project.Repositories.EventRepository;
import edu.uws.ii.project.Repositories.RecipeRepository;
import edu.uws.ii.project.domain.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService implements IRecipeService {
    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final DifficultyRepository difficultyRepository;
    private final EventRepository eventRepository;

    public RecipeService(RecipeRepository recipeRepository, CategoryRepository categoryRepository, DifficultyRepository difficultyRepository, EventRepository eventRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.difficultyRepository = difficultyRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public Optional<Recipe> getRecipeById(Long id) {
        return recipeRepository.findById(id);
    }

    @Override
    public Page<Recipe> getPage(Pageable pageable) {
        return recipeRepository.findAll(pageable);
    }

    @Override
    public void deleteRecipeById(Long id) {
        recipeRepository.deleteById(id);
    }

    @Override
    public List<Recipe> findByCategory(String category) {
        var cat = categoryRepository.findByName(category);
        return recipeRepository.findAllByCategory(cat);
    }

    @Override
    public List<Recipe> findByDifficulty(String difficulty) {
        var diff = difficultyRepository.findByName(difficulty);
        return recipeRepository.findAllByDifficulty(diff);
    }

    @Override
    public List<Recipe> findByEvent(String event) {
        var ev = eventRepository.findByName(event);
        return recipeRepository.findAllByEvents(ev);
    }
}

