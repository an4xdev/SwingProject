package edu.uws.ii.project.controllers;

import edu.uws.ii.project.Repositories.DifficultyRepository;
import edu.uws.ii.project.Repositories.IngredientRepository;
import edu.uws.ii.project.dtos.AddFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final IngredientRepository ingredientRepository;
    private final DifficultyRepository difficultyRepository;

    @Autowired
    public RecipesController(IngredientRepository ingredientRepository, DifficultyRepository difficultyRepository) {
        this.ingredientRepository = ingredientRepository;
        this.difficultyRepository = difficultyRepository;
    }
    @GetMapping
    public String addRecipe(Model model) {
        AddFormDTO recipeForm = new AddFormDTO(null, "recipe1", ingredientRepository.findAll(), difficultyRepository.findAll());
        model.addAttribute("recipeForm", recipeForm);
        return "add_recipe";
    }

    @GetMapping("/details")
    public String details() {
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

    @DeleteMapping("/{id}")
    public String deleteRecipe(@PathVariable Long id) {
        return "redirect:/add_recipe";
    }

}
