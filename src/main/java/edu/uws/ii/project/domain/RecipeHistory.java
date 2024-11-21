package edu.uws.ii.project.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

// Recipes that have been made by users
@Entity
@Getter
@Setter
@NoArgsConstructor
@Data
public class RecipeHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipeHistory;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userHistory;
}
