package edu.uws.ii.project.controllers;

import edu.uws.ii.project.Repositories.DifficultyRepository;
import edu.uws.ii.project.Repositories.IngredientRepository;
import edu.uws.ii.project.dtos.AddFormDTO;
import edu.uws.ii.project.dtos.IngredientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {

    private static final String UPLOAD_DIR = "src/main/resources/static/user_images/";

    private final IngredientRepository ingredientRepository;
    private final DifficultyRepository difficultyRepository;

    @Autowired
    public IndexController(IngredientRepository ingredientRepository, DifficultyRepository difficultyRepository) {
        this.ingredientRepository = ingredientRepository;
        this.difficultyRepository = difficultyRepository;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/details")
    public String details() {
        return "details";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/add_recipe")
    public String addRecipe(Model model) {
        AddFormDTO recipeForm = new AddFormDTO(1L, "recipe1", ingredientRepository.findAll(), difficultyRepository.findAll());
        model.addAttribute("recipeForm", recipeForm);
        return "add_recipe";
    }

    @PostMapping("/add_recipe")
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

    @GetMapping("/search")
    public String search() {
        return "search";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin-panel";
    }

    @GetMapping("/manage")
    public String manage() {
        return "manage";
    }
}
