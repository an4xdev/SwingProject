package edu.uws.ii.project.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Data
public class Steps {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer stepNumber;
    private String description;

    @ManyToOne
    @JoinColumn(name = "recipe_step_id")
    private Recipe recipeStep;

    public Steps(Integer stepNumber, String description) {
        this.stepNumber = stepNumber;
        this.description = description;
    }
}
