package edu.uws.ii.project.dtos;

import edu.uws.ii.project.domain.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class FormDTO {
    @NotNull
    private Long id;

    @NotEmpty
    @Size(min = 3, max = 50)
    private String name;

    @NotEmpty
    @Size(min = 3, max = 500)
    private String description;

    private Boolean requireOven;

    private List<Ingredient> ingredients;
    private List<Ingredient> ingredientsAdded;

    @NotNull
    private MultipartFile image;

    @Min(value = 1)
    private Long difficultyId;

    @NotNull
    @Size(min = 3, max = 50)
    private List<Step> steps;

    @NotNull
    @Size(min = 1)
    private List<Long> eventIds;

    @Min(value = 1)
    private Long categoryId;

    private Time time;

    public FormDTO(Long id, String name) {
        this.id = id;
        this.name = name;
        this.ingredients = new ArrayList<>();
        this.ingredientsAdded = new ArrayList<>();
        this.steps = new ArrayList<>();
        this.eventIds = new ArrayList<>();
        time = new Time();
    }

    public FormDTO(Recipe recipe, List<Step> steps) {
        this.id = recipe.getId();
        this.name = recipe.getName();
        this.description = recipe.getDescription();
        this.time = recipe.getTime();
        this.requireOven = recipe.getRequireOven();
        this.categoryId = recipe.getCategory().getId();
        this.difficultyId = recipe.getDifficulty().getId();
        this.eventIds = recipe.getEvents().stream().map(Event::getId).toList();
        this.ingredients = new ArrayList<>(recipe.getIngredients());
        this.ingredientsAdded = new ArrayList<>();
        this.steps = new ArrayList<>(steps);
    }
}