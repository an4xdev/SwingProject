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

    // TODO: move to properties file?
    @DecimalMin(value = "0.01", message = "Value must be at least 0.01")
    @DecimalMax(value = "9.99", message = "Value must not exceed 9.99")
    private Float rating;
}
