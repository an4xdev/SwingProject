package edu.uws.ii.project.controllers;

import edu.uws.ii.project.domain.*;
import edu.uws.ii.project.dtos.FormDTO;
import edu.uws.ii.project.dtos.RateRecipeDTO;
import edu.uws.ii.project.editors.CustomTimeEditor;
import edu.uws.ii.project.exceptions.RecipeNotFound;
import edu.uws.ii.project.services.categories.ICategoryService;
import edu.uws.ii.project.services.comments.ICommentService;
import edu.uws.ii.project.services.difficulties.IDifficultyService;
import edu.uws.ii.project.services.events.IEventService;
import edu.uws.ii.project.services.favourites.IFavouriteService;
import edu.uws.ii.project.services.ingredients.IIngredientsService;
import edu.uws.ii.project.services.rating.IRatingService;
import edu.uws.ii.project.services.recipe_history.IRecipeHistoryService;
import edu.uws.ii.project.services.recipes.IRecipeService;
import edu.uws.ii.project.services.steps.IStepService;
import edu.uws.ii.project.validators.FormDTOValidator;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Log4j2
@Controller
@RequestMapping("/recipes")
public class RecipeController {

    private final IRecipeService recipeService;
    private final ICommentService commentService;
    private final IIngredientsService ingredientsService;
    private final IStepService stepService;
    private final IDifficultyService difficultyService;
    private final IFavouriteService favouriteService;
    private final IRecipeHistoryService recipeHistoryService;
    private final IEventService eventService;
    private final ICategoryService categoryService;
    private final IRatingService ratingService;
    private final FormDTOValidator formDTOValidator;

    @Autowired
    public RecipeController(IRecipeService recipeService, ICommentService commentService, IIngredientsService ingredientsService, IStepService stepService, IDifficultyService difficultyService, IFavouriteService favouriteService, IRecipeHistoryService recipeHistoryService, IEventService eventService, ICategoryService categoryService, IRatingService ratingService, FormDTOValidator formDTOValidator) {
        this.recipeService = recipeService;
        this.ingredientsService = ingredientsService;
        this.stepService = stepService;
        this.difficultyService = difficultyService;
        this.commentService = commentService;
        this.favouriteService = favouriteService;
        this.recipeHistoryService = recipeHistoryService;
        this.eventService = eventService;
        this.categoryService = categoryService;
        this.ratingService = ratingService;
        this.formDTOValidator = formDTOValidator;
    }

    @GetMapping("/page")
    public String indexPaged(@RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "6") int size, Model model) {
        Page<Recipe> recipes = recipeService.getPage(PageRequest.of(page, size));
        model.addAttribute("recipes", recipes);
        return "index";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        var recipe = recipeService
                .findById(id)
                .orElseThrow(
                        () -> new RecipeNotFound("In search of recipe with id: " + id)
                );
        var favourites = favouriteService.getFavouriteCountByRecipe(recipe);
        var done = recipeHistoryService.getDoneCountByRecipe(recipe);
        var comments = commentService.findAllByRecipeId(id);
        var rating = ratingService.calculateRatingByRecipe(recipe);

        var alreadyFavourite = favouriteService.isFavourite(recipe);
        var alreadyDone = recipeHistoryService.isDone(recipe);

        var userRating = ratingService.getRatingByRecipe(recipe);
        var rateRecipe = new RateRecipeDTO(id, userRating);

        model.addAttribute("recipe", recipe);
        model.addAttribute("favourites", favourites);
        model.addAttribute("done", done);
        model.addAttribute("comments", comments);
        model.addAttribute("rating", rating);

        model.addAttribute("alreadyFavourite", alreadyFavourite);
        model.addAttribute("alreadyDone", alreadyDone);

        model.addAttribute("rateRecipe", rateRecipe);
        return "details";
    }

    @ModelAttribute("difficulties")
    public List<Difficulty> difficulties() {
        return difficultyService.findAll();
    }

    @ModelAttribute("categories")
    public List<Category> categories() {
        return categoryService.findAll();
    }

    @ModelAttribute("ingredients")
    public List<Ingredient> ingredients() {
        return ingredientsService.findAll();
    }

    @ModelAttribute("events")
    public List<Event> events() {
        return eventService.findAll();
    }

    @ModelAttribute("eventsIds")
    public List<Long> eventsIds() {
        return eventService.findAll().stream().map(Event::getId).toList();
    }

    @GetMapping({"/form", "/form/{id}"})
    public String addRecipeShowForm(Model model, @PathVariable(required = false) Long id) {
        FormDTO recipeForm = new FormDTO(-1L, "");

        if (id != null) {
            var recipe = recipeService.findById(id).orElseThrow();
            var steps = stepService.findAllByRecipeId(id);
            recipeForm = new FormDTO(recipe, steps);
            model.addAttribute("recipeUser", recipe.getUser().getUsername());
        } else {
            model.addAttribute("recipeUser", "");
        }

        model.addAttribute("recipeForm", recipeForm);

        model.addAttribute("title", id == null ? "Add recipe" : "Edit recipe");

        model.addAttribute("editing", id != null);

        return "recipe_form";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Time.class, new CustomTimeEditor());
    }

    private String saveImage(MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            try {
                final String UPLOAD_DIR = "src/main/resources/static/user_images/";
                Files.createDirectories(Paths.get(UPLOAD_DIR));

                Path filePath = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
                Files.write(filePath, file.getBytes());

                final String PATH_TO_IMAGES = "/user_images/";
                return PATH_TO_IMAGES + file.getOriginalFilename();

            } catch (IOException e) {
                log.log(Level.ERROR, "Error while saving image", e);
                throw new RuntimeException("Error while saving image");
            }
        }
        return null;
    }

    @PostMapping("/form")
    public String addRecipe(@Valid @ModelAttribute("recipeForm") FormDTO recipeForm, BindingResult bindingResult) {

        formDTOValidator.validateIngredients(recipeForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "recipe_form";
        }

        String photoPath = saveImage(recipeForm.getImage());

        recipeService.save(recipeForm, photoPath);

        return "redirect:/";
    }

    @PutMapping("/form")
    public String updateRecipe(@ModelAttribute("recipeForm") FormDTO recipeForm, BindingResult bindingResult) {

        formDTOValidator.validateIngredients(recipeForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "recipe_form";
        }

        String photoPath = recipeForm.getImage() != null ? saveImage(recipeForm.getImage()) : null;

        var recipe = recipeService.findById(recipeForm.getId()).orElseThrow();

        recipeService.update(recipeForm, recipe, photoPath);

        return "redirect:/";
    }

    @GetMapping("/category/{category}")
    public String recipesByCategory(@PathVariable String category, Model model) {
        var recipes = recipeService.findByCategory(category);
        model.addAttribute("type", "category");
        model.addAttribute("name", category);
        model.addAttribute("recipes", recipes);
        return "categorised";
    }

    @GetMapping("/difficulty/{difficulty}")
    public String recipesByDifficulty(@PathVariable String difficulty, Model model) {
        var recipes = recipeService.findByDifficulty(difficulty);
        model.addAttribute("type", "difficulty");
        model.addAttribute("name", difficulty);
        model.addAttribute("recipes", recipes);
        return "categorised";
    }

    @GetMapping("/event/{event}")
    public String recipesByEvent(@PathVariable String event, Model model) {
        var recipes = recipeService.findByEvent(event);
        model.addAttribute("type", "event");
        model.addAttribute("name", event);
        model.addAttribute("recipes", recipes);
        return "categorised";
    }

    @DeleteMapping("/{id}")
    public String deleteRecipe(@PathVariable Long id) {
        var recipe = recipeService
                .findById(id)
                .orElseThrow(
                        () -> new RecipeNotFound("In deleting of recipe with id: " + id)
                );
        recipeService.delete(recipe);
        return "redirect:/";
    }

    @GetMapping("/user")
    public String userRecipes(Model model) {
        var recipes = recipeService.findByUser();
        model.addAttribute("recipes", recipes);
        return "user_recipes";
    }

}
