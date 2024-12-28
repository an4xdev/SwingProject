package edu.uws.ii.project.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoryRecipeDTO {
    private Long recipeId;
    private String recipeName;
    private String recipeDescription;
    private String recipeImage;
    private LocalDateTime date;
}
