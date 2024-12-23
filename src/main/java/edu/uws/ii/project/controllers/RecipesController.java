package edu.uws.ii.project.controllers;

import edu.uws.ii.project.domain.*;
import edu.uws.ii.project.dtos.AddFormDTO;
import edu.uws.ii.project.services.categories.ICategoryService;
import edu.uws.ii.project.services.comments.ICommentService;
import edu.uws.ii.project.services.difficulties.IDifficultyService;
import edu.uws.ii.project.services.events.IEventService;
import edu.uws.ii.project.services.favourites.IFavouriteService;
import edu.uws.ii.project.services.ingredients.IIngredientsService;
import edu.uws.ii.project.services.recipe_history.IRecipeHistoryService;
import edu.uws.ii.project.services.recipes.IRecipeService;
import edu.uws.ii.project.services.steps.IStepService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/recipes")
public class RecipesController {

    private static final String UPLOAD_DIR = "src/main/resources/static/user_images/";

    private final IRecipeService recipeService;
    private final ICommentService commentService;
    private final IIngredientsService ingredientsService;
    private final IStepService stepService;
    private final IDifficultyService difficultyService;
    private final IFavouriteService favouriteService;
    private final IRecipeHistoryService recipeHistoryService;
    private final IEventService eventService;
    private final ICategoryService categoryService;

    @Autowired
    public RecipesController(IRecipeService recipeService, ICommentService commentService, IIngredientsService ingredientsService, IStepService stepService, IDifficultyService difficultyService, IFavouriteService favouriteService, IRecipeHistoryService recipeHistoryService, IEventService eventService, ICategoryService categoryService) {
        this.recipeService = recipeService;
        this.ingredientsService = ingredientsService;
        this.stepService = stepService;
        this.difficultyService = difficultyService;
        this.commentService = commentService;
        this.favouriteService = favouriteService;
        this.recipeHistoryService = recipeHistoryService;
        this.eventService = eventService;
        this.categoryService = categoryService;
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
        var recipe = recipeService.getRecipeById(id).orElseThrow();
        var favourites = favouriteService.getFavouriteCountByRecipe(recipe);
        var done = recipeHistoryService.getDoneCountByRecipe(recipe);
        var ingredients = ingredientsService.findAllByRecipe(recipe);
        var steps = stepService.findAllByRecipeId(id);
        var comments = commentService.findAllByRecipeId(id);
        model.addAttribute("recipe", recipe);
        model.addAttribute("favourites", favourites);
        model.addAttribute("done", done);
        model.addAttribute("ingredients", ingredients);
        model.addAttribute("steps", steps);
        model.addAttribute("comments", comments);
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

    // TODO: add optional parameter for editing recipe?
    @GetMapping("/form")
    public String addRecipeShowForm(Model model) {
        AddFormDTO recipeForm = new AddFormDTO(-1L, "");
        model.addAttribute("recipeForm", recipeForm);
        return "recipe_form";
    }

    @PostMapping("/form")
    public String addRecipe(@Valid @ModelAttribute("recipeForm") AddFormDTO recipeForm, BindingResult bindingResult) {

        if (recipeForm.getIngredients() == null || recipeForm.getIngredients().isEmpty()) {
            bindingResult.rejectValue("ingredients", "error.recipeForm", "At least one ingredient is required");
        }

        if (recipeForm.getIngredientsAdded() == null || recipeForm.getIngredientsAdded().isEmpty()) {
            bindingResult.rejectValue("ingredientsAdded", "error.recipeForm", "At least one added ingredient is required");
        }

        if (bindingResult.hasErrors()) {
            return "recipe_form";
        }


//        MultipartFile file = recipeForm.getImage();
//
//        if (file != null && !file.isEmpty()) {
//            try {
//                Files.createDirectories(Paths.get(UPLOAD_DIR));
//
//                Path filePath = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
//                Files.write(filePath, file.getBytes());
//
//            } catch (IOException e) {
//                e.printStackTrace();
//                return "redirect:/add_recipe";
//            }
//        }

        return "redirect:/recipes/form";
    }

    @PutMapping("/form")
    public String updateRecipe(@ModelAttribute("recipeForm") AddFormDTO recipeForm) {
        MultipartFile file = recipeForm.getImage();

        if (file != null && !file.isEmpty()) {
            try {
                Files.createDirectories(Paths.get(UPLOAD_DIR));

                Path filePath = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
                Files.write(filePath, file.getBytes());

            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/recipes/form";
            }
        }

        return "redirect:/recipes/form";
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
        recipeService.deleteRecipeById(id);
        return "redirect:back";
    }

}
