package edu.uws.ii.project.services.recipes;

import edu.uws.ii.project.Repositories.CategoryRepository;
import edu.uws.ii.project.Repositories.DifficultyRepository;
import edu.uws.ii.project.Repositories.EventRepository;
import edu.uws.ii.project.Repositories.RecipeRepository;
import edu.uws.ii.project.domain.Ingredient;
import edu.uws.ii.project.domain.Recipe;
import edu.uws.ii.project.domain.Step;
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

    private List<Ingredient> getIngredients(FormDTO recipeForm) {
        List<Ingredient> ingredientsToRecipe = new ArrayList<>();

        if (recipeForm.getIngredientsAdded() != null) {
            for (Ingredient ingredient : recipeForm.getIngredientsAdded()) {
                if (ingredient.getName() == null || ingredient.getName().isBlank()) {
                    continue;
                }
                Ingredient saved = ingredientsService.save(ingredient);
                ingredientsToRecipe.add(saved);
            }
        }

        if (recipeForm.getIngredients() != null) {
            ingredientsToRecipe.addAll(recipeForm.getIngredients());
        }

        return ingredientsToRecipe;
    }

    private List<Step> getSteps(FormDTO recipeForm, Recipe recipe) {
        List<Step> stepsToRecipe = new ArrayList<>();

        if (recipeForm.getSteps() != null) {
            for (Step step : recipeForm.getSteps()) {
                if (step.getDescription() == null || step.getDescription().isBlank()) {
                    continue;
                }
                step.setRecipe(recipe);
                stepsToRecipe.add(step);
            }
        }

        return stepsToRecipe;
    }

    @Override
    @Transactional
    public void save(FormDTO formDTO, String photoPath) {
        List<Ingredient> ingredientsToRecipe = getIngredients(formDTO);
        List<Step> stepsToRecipe = getSteps(formDTO, null);

        Recipe recipe = new Recipe(
                formDTO.getName(),
                formDTO.getDescription(),
                photoPath,
                formDTO.getTime(),
                formDTO.getRequireOven(),
                LocalDateTime.now(),
                userService.getCurrentUser(),
                new HashSet<>(ingredientsToRecipe),
                categoryService.findById(formDTO.getCategoryId()),
                difficultyService.findById(formDTO.getDifficultyId()),
                new HashSet<>(eventService.findAllByIds(formDTO.getEventIds()))
        );

        recipeRepository.save(recipe);

        for (Step step : stepsToRecipe) {
            step.setRecipe(recipe);
        }
        stepService.saveAll(stepsToRecipe);
    }

    @Override
    @Transactional
    public void update(FormDTO formDTO, Recipe recipe, String photoPath) {
        List<Ingredient> validIngredients = formDTO.getIngredients().stream()
                .filter(ingredient -> ingredient.getId() != null)
                .toList();

        formDTO.setIngredients(validIngredients);

        System.out.println("------------------------------");
        System.out.println("------------------------------");
        System.out.println(formDTO);
        System.out.println("------------------------------");
        System.out.println("------------------------------");

        List<Ingredient> ingredientsToRecipe = getIngredients(formDTO);

        stepService.deleteAllByRecipeId(recipe.getId());
        List<Step> stepsToRecipe = getSteps(formDTO, recipe);

        recipe.setName(formDTO.getName());
        recipe.setDescription(formDTO.getDescription());
        recipe.setPhotoPath(photoPath);
        recipe.setTime(formDTO.getTime());
        recipe.setRequireOven(formDTO.getRequireOven());
        recipe.setIngredients(new HashSet<>(ingredientsToRecipe));
        recipe.setCategory(categoryService.findById(formDTO.getCategoryId()));
        recipe.setDifficulty(difficultyService.findById(formDTO.getDifficultyId()));
        recipe.setEvents(new HashSet<>(eventService.findAllByIds(formDTO.getEventIds())));

        recipeRepository.save(recipe);

        for (Step step : stepsToRecipe) {
            step.setRecipe(recipe);
        }
        stepService.saveAll(stepsToRecipe);
    }

    @Override
    public List<Recipe> findByUser() {
        return recipeRepository.findAllByUser(userService.getCurrentUser());
    }
}

