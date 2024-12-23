package edu.uws.ii.project.dtos;

import edu.uws.ii.project.domain.Ingredient;
import edu.uws.ii.project.domain.Step;
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
public class AddFormDTO {
    @NotNull(message = "Unknown error, please contact the administrator")
    private Long id;

    @NotEmpty(message = "Name is required")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;

    @NotEmpty(message = "Description is required")
    @Size(min = 3, max = 500, message = "Description must be between 3 and 500 characters")
    private String description;


    private List<Ingredient> ingredients;
    private List<Ingredient> ingredientsAdded;

    @NotNull(message = "Image is required")
    private MultipartFile image;

    @Min(value = 1, message = "Difficulty is required")
    private Long difficultyId;

    @Size(min = 3, max = 50, message = "Steps must contain between 3 and 50 steps")
    private List<Step> steps;

    @Size(min = 1, message = "Events must contain at least 1 event")
    private List<Long> eventIds;

    @Min(value = 1, message = "Category is required")
    private Long categoryId;

    @Min(value = 1, message = "Time is required")
    private Long time;

    public AddFormDTO(Long id, String name) {
        this.id = id;
        this.name = name;
        this.ingredients = new ArrayList<>();
        this.ingredientsAdded = new ArrayList<>();
        this.steps = new ArrayList<>();
        this.eventIds = new ArrayList<>();
    }

}
