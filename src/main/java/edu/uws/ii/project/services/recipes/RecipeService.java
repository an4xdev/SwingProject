package edu.uws.ii.project.services.recipes;

import edu.uws.ii.project.Repositories.CategoryRepository;
import edu.uws.ii.project.Repositories.DifficultyRepository;
import edu.uws.ii.project.Repositories.EventRepository;
import edu.uws.ii.project.Repositories.RecipeRepository;
import edu.uws.ii.project.domain.*;
import edu.uws.ii.project.dtos.FormDTO;
import edu.uws.ii.project.dtos.SearchFormDTO;
import edu.uws.ii.project.services.categories.ICategoryService;
import edu.uws.ii.project.services.comments.ICommentService;
import edu.uws.ii.project.services.difficulties.IDifficultyService;
import edu.uws.ii.project.services.events.IEventService;
import edu.uws.ii.project.services.favourites.FavouriteService;
import edu.uws.ii.project.services.ingredients.IIngredientsService;
import edu.uws.ii.project.services.rating.IRatingService;
import edu.uws.ii.project.services.recipe_history.IRecipeHistoryService;
import edu.uws.ii.project.services.steps.IStepService;
import edu.uws.ii.project.services.user.IUserService;
import edu.uws.ii.project.specifications.RecipeSearchSpecification;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeService implements IRecipeService {
    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final DifficultyRepository difficultyRepository;
    private final EventRepository eventRepository;
    private final IIngredientsService ingredientsService;
    private final ICategoryService categoryService;
    private final IEventService eventService;
    private final IDifficultyService difficultyService;
    private final IStepService stepService;
    private final IUserService userService;
    private final ICommentService commentService;
    private final FavouriteService favouriteService;
    private final IRecipeHistoryService recipeHistoryService;
    private final IRatingService ratingService;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository, CategoryRepository categoryRepository, DifficultyRepository difficultyRepository, EventRepository eventRepository, IIngredientsService ingredientsService, ICategoryService categoryService, IEventService eventService, IDifficultyService difficultyService, IStepService stepService, IUserService userService, ICommentService commentService, FavouriteService favouriteService, IRecipeHistoryService recipeHistoryService, IRatingService ratingService) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.difficultyRepository = difficultyRepository;
        this.eventRepository = eventRepository;
        this.ingredientsService = ingredientsService;
        this.categoryService = categoryService;
        this.eventService = eventService;
        this.difficultyService = difficultyService;
        this.stepService = stepService;
        this.userService = userService;
        this.commentService = commentService;
        this.favouriteService = favouriteService;
        this.recipeHistoryService = recipeHistoryService;
        this.ratingService = ratingService;
    }

    @Override
    public Optional<Recipe> findById(Long id) {
        return recipeRepository.findById(id);
    }

    @Override
    public Page<Recipe> getPage(Pageable pageable) {
        Pageable sortedByDate = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by("createdAt").descending());
        return recipeRepository.findAll(sortedByDate);
    }

    @Override
    @Transactional
    public void delete(Recipe recipe) {

        commentService.deleteByRecipeId(recipe.getId());
        recipe.getComments().clear();

        for (Step step : recipe.getSteps()) {
            stepService.delete(step);
        }
        recipe.getSteps().clear();

        recipeHistoryService.deleteByRecipeId(recipe.getId());
        recipe.getRecipeHistories().clear();

        favouriteService.deleteByRecipeId(recipe.getId());
        recipe.getFavourite().clear();

        ratingService.deleteByRecipeId(recipe.getId());
        recipe.getRatings().clear();


        recipeRepository.save(recipe);

        recipeRepository.delete(recipe);
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
        return recipeRepository.findAllByEvents(new HashSet<>(List.of(ev)));
    }

    @Override
    public List<Recipe> findBySpecification(SearchFormDTO searchFormDTO) {

        Specification<Recipe> spec = Specification
                .where(RecipeSearchSpecification
                        .findByPhrase(
                                searchFormDTO.getPhrase()
                        )
                ).and(RecipeSearchSpecification
                        .findByEventsIds(
                                searchFormDTO.getEventIds()
                        )
                );

        if (searchFormDTO.getDifficultyId() != 0) {
            spec = spec.and(RecipeSearchSpecification
                    .findByDifficultyId(
                            searchFormDTO.getDifficultyId()
                    )
            );
        }

        if (searchFormDTO.getCategoryId() != 0) {
            spec = spec.and(RecipeSearchSpecification
                    .findByCategoryId(
                            searchFormDTO.getCategoryId()
                    )
            );
        }

        Sort sort = Sort.unsorted();
        if (searchFormDTO.getSortType() != null) {
            sort = switch (searchFormDTO.getSortType().intValue()) {
                case 1 -> Sort.by(Sort.Direction.DESC, "createdAt");
                case 2 -> Sort.by(Sort.Direction.ASC, "createdAt");
                case 3 -> Sort.by(Sort.Direction.ASC, "name");
                case 4 -> Sort.by(Sort.Direction.ASC, "difficulty.id");
                default -> sort;
            };
        }
        return recipeRepository.findAll(spec, sort).stream().distinct().collect(Collectors.toList());
    }

    private ArrayList<Ingredient> getIngredients(FormDTO recipeForm) {
        ArrayList<Ingredient> ingredientsToRecipe = new ArrayList<>();

        if (recipeForm.getIngredientsAdded() != null) {
            if (!recipeForm.getIngredientsAdded().isEmpty()) {
                for (Ingredient ingredient : recipeForm.getIngredientsAdded()) {
                    // skip empty ingredients
                    // another validation after js validation
                    if (ingredient.getName().isBlank()) {
                        continue;
                    }
                    ingredientsToRecipe.add(ingredientsService.save(ingredient));
                }
            }
        }

        // add existing ingredients to recipe

        if (recipeForm.getIngredients() != null) {
            ingredientsToRecipe.addAll(recipeForm.getIngredients());
        }

        return ingredientsToRecipe;
    }

    private ArrayList<Step> getSteps(FormDTO recipeForm) {
        ArrayList<Step> stepsToRecipe = new ArrayList<>();

        if (recipeForm.getSteps() != null) {
            for (var step : recipeForm.getSteps()) {
                if (step.getDescription().isBlank()) {
                    continue;
                }
                stepsToRecipe.add(step);
            }
        }

        return stepsToRecipe;
    }

    @Override
    public void save(FormDTO recipeForm, String photoPath) {
        ArrayList<Ingredient> ingredientsToRecipe = getIngredients(recipeForm);

        // get category by id

        Category category = categoryService.findById(recipeForm.getCategoryId());

        // get difficulty by id

        Difficulty difficulty = difficultyService.findById(recipeForm.getDifficultyId());

        // get events by ids

        List<Event> events = eventService.findAllByIds(recipeForm.getEventIds());

        // get current user

        User user = userService.getCurrentUser();

        // validate steps

        ArrayList<Step> stepsToRecipe = getSteps(recipeForm);

        // create new recipe

        Recipe recipe = new Recipe(recipeForm.getName(), recipeForm.getDescription(), photoPath, recipeForm.getTime(), recipeForm.getRequireOven(), LocalDateTime.now(), user, new HashSet<>(ingredientsToRecipe), category, difficulty, new HashSet<>(events));
        recipe.setId(null);

        // save recipe

        recipeRepository.save(recipe);

        // save steps

        for (var step : stepsToRecipe) {
            step.setRecipe(recipe);
        }

        stepService.saveAll(stepsToRecipe);

        // Save the recipe again with steps

        recipeRepository.save(recipe);
    }

    @Override
    public void update(FormDTO recipeForm, Recipe recipe, String photoPath) {
        ArrayList<Ingredient> ingredientsToRecipe = getIngredients(recipeForm);

        // get category by id

        Category category = categoryService.findById(recipeForm.getCategoryId());

        // get difficulty by id

        Difficulty difficulty = difficultyService.findById(recipeForm.getDifficultyId());

        // get events by ids

        List<Event> events = eventService.findAllByIds(recipeForm.getEventIds());

        // validate steps

        ArrayList<Step> stepsToRecipe = getSteps(recipeForm);

        // update recipe fields

        recipe.setName(recipeForm.getName());
        recipe.setDescription(recipeForm.getDescription());
        recipe.setPhotoPath(photoPath);
        recipe.setTime(recipeForm.getTime());
        recipe.setRequireOven(recipeForm.getRequireOven());
        recipe.setIngredients(new HashSet<>(ingredientsToRecipe));
        recipe.setCategory(category);
        recipe.setDifficulty(difficulty);
        recipe.setEvents(new HashSet<>(events));

        // update recipe

        recipeRepository.save(recipe);

        // save steps

        for (var step : stepsToRecipe) {
            step.setRecipe(recipe);
        }

        stepService.saveAll(stepsToRecipe);

        // update the recipe again with steps

        recipeRepository.save(recipe);
    }
}

