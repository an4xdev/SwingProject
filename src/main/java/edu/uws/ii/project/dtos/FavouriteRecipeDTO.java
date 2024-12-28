package edu.uws.ii.project.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FavouriteRecipeDTO {
    public Long recipeId;
    public String recipeName;
    public String recipeDescription;
    public String recipeImage;
}
