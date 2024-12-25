package edu.uws.ii.project.controllers;

import edu.uws.ii.project.services.profile.IProfileService;
import edu.uws.ii.project.services.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final IProfileService profileService;
    private final IUserService userService;

    @Autowired
    public ProfileController(IProfileService profileService, IUserService userService) {
        this.profileService = profileService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String profile(Model model) {
        var userId = userService.getCurrentUserId();

        var userRecipesCount = profileService.getCountOfUserRecipes(userId);
        model.addAttribute("userRecipesCount", userRecipesCount);

        var userFavouritesCount = profileService.getCountOfUserFavouritesRecipes(userId);
        model.addAttribute("userFavouritesCount", userFavouritesCount);

        var userDoneCount = profileService.getCountOfUserDoneRecipes(userId);
        model.addAttribute("userDoneCount", userDoneCount);

        var username = userService.getCurrentUsername();
        model.addAttribute("username", username);

        var email = userService.getCurrentUserEmail();
        model.addAttribute("email", email);

        return "profile";
    }
}
