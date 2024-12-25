package edu.uws.ii.project.controllers;

import edu.uws.ii.project.dtos.RateRecipeDTO;
import edu.uws.ii.project.services.rating.IRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ratings")
public class RatingController {
    private final IRatingService ratingService;

    @Autowired
    public RatingController(IRatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/")
    public String rate(@ModelAttribute RateRecipeDTO rateRecipeDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/recipes/" + rateRecipeDTO.getRecipeId();
        }
        ratingService.rate(rateRecipeDTO.getRecipeId(), rateRecipeDTO.getRating());
        return "redirect:/recipes/" + rateRecipeDTO.getRecipeId();
    }
}
