package edu.uws.ii.project.dtos;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RateRecipeDTO {
    private Long recipeId;

    @DecimalMin(value = "0.01")
    @DecimalMax(value = "9.99")
    private Float rating;
}
