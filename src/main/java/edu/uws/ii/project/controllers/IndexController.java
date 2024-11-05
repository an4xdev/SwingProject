package edu.uws.ii.project.controllers;

import edu.uws.ii.project.dtos.AddFormDTO;
import edu.uws.ii.project.dtos.IngredientDTO;
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
        List<IngredientDTO> ingredientDTOList = new ArrayList<>() {{
            add(new IngredientDTO(1L, "ingredient1"));
            add(new IngredientDTO(2L, "ingredient2"));
        }};
        AddFormDTO recipeForm = new AddFormDTO(1L, "recipe1", ingredientDTOList, new ArrayList<>());
        model.addAttribute("recipeForm", recipeForm);
        return "add_recipe";
    }

    @PostMapping("/add_recipe")
    public String addRecipe(@ModelAttribute("recipeForm") AddFormDTO recipeForm) {
        MultipartFile[] files = recipeForm.getImages();

        if(files != null) {
            for(MultipartFile file : files) {
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
