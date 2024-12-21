package edu.uws.ii.project.controllers;

import edu.uws.ii.project.domain.Recipe;
import edu.uws.ii.project.dtos.AddFormDTO;
import edu.uws.ii.project.services.comments.ICommentService;
import edu.uws.ii.project.services.difficulties.IDifficultyService;
import edu.uws.ii.project.services.ingredients.IIngredientsService;
import edu.uws.ii.project.services.recipes.IRecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/recipes")
public class RecipesController {

    private static final String UPLOAD_DIR = "src/main/resources/static/user_images/";

    private final IRecipeService recipeService;
    private final ICommentService commentService;
    private final IIngredientsService ingredientsService;
    private final IDifficultyService difficultyService;

    @Autowired
    public RecipesController(IRecipeService recipeService, ICommentService commentService, IIngredientsService ingredientsService, IDifficultyService difficultyService) {
        this.recipeService = recipeService;
        this.ingredientsService = ingredientsService;
        this.difficultyService = difficultyService;
        this.commentService = commentService;
    }

    @GetMapping("/page")
    public String indexPaged(@RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "6") int size, Model model) {
        Page<Recipe> recipes = recipeService.getPage(PageRequest.of(page, size));
        model.addAttribute("recipes", recipes);
        return "index";
    }

    @GetMapping
    public String addRecipe(Model model) {
        AddFormDTO recipeForm = new AddFormDTO(null, "", ingredientsService.findAll(), difficultyService.findAll());
        model.addAttribute("recipeForm", recipeForm);
        return "add_recipe";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable Long id, Model model) {
        var recipe = recipeService.getRecipeById(id).orElseThrow();
        var comments = commentService.findAllByRecipeId(id);
        model.addAttribute("recipe", recipe);
        model.addAttribute("comments", comments);
        return "details";
    }

    @PostMapping
    public String addRecipe(@ModelAttribute("recipeForm") AddFormDTO recipeForm) {
        MultipartFile file = recipeForm.getImage();

        if (file != null && !file.isEmpty()) {
            try {
                Files.createDirectories(Paths.get(UPLOAD_DIR));

                Path filePath = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
                Files.write(filePath, file.getBytes());

            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/add_recipe";
            }
        }

        return "redirect:/add_recipe";
    }

    @PutMapping
    public String updateRecipe(@ModelAttribute("recipeForm") AddFormDTO recipeForm) {
        MultipartFile file = recipeForm.getImage();

        if (file != null && !file.isEmpty()) {
            try {
                Files.createDirectories(Paths.get(UPLOAD_DIR));

                Path filePath = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
                Files.write(filePath, file.getBytes());

            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/add_recipe";
            }
        }

        return "redirect:/add_recipe";
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
        return "redirect:/add_recipe";
    }

}
