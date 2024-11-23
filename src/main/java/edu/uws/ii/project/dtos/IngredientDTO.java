package edu.uws.ii.project.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class IngredientDTO {
    private Long id;
    private String name;

    public IngredientDTO() {
    }

    public IngredientDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}
