package edu.uws.ii.project.services.recipe_history;

import edu.uws.ii.project.Repositories.RecipeHistoryRepository;
import edu.uws.ii.project.Repositories.RecipeRepository;
import edu.uws.ii.project.domain.Recipe;
import edu.uws.ii.project.domain.RecipeHistory;
import edu.uws.ii.project.dtos.HistoryRecipeDTO;
import edu.uws.ii.project.exceptions.RecipeNotFound;
import edu.uws.ii.project.services.user.IUserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeHistoryService implements IRecipeHistoryService {

    private final RecipeHistoryRepository recipeHistoryRepository;
    private final IUserService userService;
    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeHistoryService(RecipeHistoryRepository recipeHistoryRepository, IUserService userService, RecipeRepository recipeRepository) {
        this.recipeHistoryRepository = recipeHistoryRepository;
        this.userService = userService;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Long getDoneCountByRecipe(Recipe recipe) {
        return recipeHistoryRepository.countRecipeHistoriesByRecipe(recipe);
    }

    @Override
    public boolean isDone(Recipe recipe) {
        return recipeHistoryRepository.existsByRecipeAndUser(recipe, userService.getCurrentUser());
    }

    @Override
    public void add(Long recipeId) {
        var user = userService.getCurrentUser();
        var recipe = recipeRepository.findById(recipeId).orElseThrow(
                () -> new RecipeNotFound("In adding recipe to history with id: " + recipeId + " not found")
        );
        RecipeHistory recipeHistory = new RecipeHistory(user, recipe);
        recipeHistoryRepository.save(recipeHistory);
    }

    @Override
    @Transactional
    public void delete(Long recipeId) {
        var user = userService.getCurrentUser();
        var recipe = recipeRepository.findById(recipeId).orElseThrow(
                () -> new RecipeNotFound("In deleting recipe from history with id: " + recipeId + " not found")
        );
        recipeHistoryRepository.deleteByRecipeAndUser(recipe, user);
    }

    @Override
    public void deleteByRecipeId(Long id) {
        recipeHistoryRepository.deleteByRecipe_Id(id);
    }

    @Override
    public List<HistoryRecipeDTO> getUserHistory() {
        var user = userService.getCurrentUser();
        var history = recipeHistoryRepository.findAllByUserId(user.getId());
        return history.stream().map(h -> {
            var recipe = h.getRecipe();
            var dto = new HistoryRecipeDTO();
            dto.setRecipeId(h.getId());
            dto.setRecipeName(recipe.getName());
            dto.setRecipeDescription(recipe.getDescription());
            dto.setRecipeImage(recipe.getPhotoPath());
            dto.setDate(h.getDate());
            return dto;
        }).collect(Collectors.toList());
    }
}
