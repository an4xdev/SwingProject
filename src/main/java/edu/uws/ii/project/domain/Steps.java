package edu.uws.ii.project.domain;

import jakarta.persistence.*;

@Entity
public class Steps {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer stepNumber;
    private String description;
    private String photoPath;

    @ManyToOne
    @JoinColumn(name = "recipe_step_id")
    private Recipe recipeStep;
}
