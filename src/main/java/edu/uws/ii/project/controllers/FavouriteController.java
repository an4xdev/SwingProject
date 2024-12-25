package edu.uws.ii.project.controllers;

import edu.uws.ii.project.services.favourites.IFavouriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/favourites")
public class FavouriteController {
    private final IFavouriteService favouriteService;

    @Autowired
    public FavouriteController(IFavouriteService favouriteService) {
        this.favouriteService = favouriteService;
    }

    @PostMapping("/add")
    public String add(@RequestParam Long recipeId) {
        favouriteService.add(recipeId);
        return "redirect:/recipes/" + recipeId;
    }

    @PostMapping("/remove")
    public String remove(@RequestParam Long recipeId) {
        favouriteService.remove(recipeId);
        return "redirect:/recipes/" + recipeId;
    }
}
