package edu.uws.ii.project.dtos;

import edu.uws.ii.project.domain.Difficulty;
import edu.uws.ii.project.domain.Ingredient;
import edu.uws.ii.project.domain.Step;
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
    private Long id;
    private String name;
    private List<Ingredient> ingredients;
    private MultipartFile image;
    private List<Ingredient> ingredientsAdded;
    private List<Difficulty> difficulties;
    private List<Step> steps;

    public AddFormDTO(Long id, String name, List<Ingredient> ingredients, List<Difficulty> difficulties) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.difficulties = difficulties;
        this.ingredientsAdded = new ArrayList<>();
        this.steps = new ArrayList<>();
    }

}
