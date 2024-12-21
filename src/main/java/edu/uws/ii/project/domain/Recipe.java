package edu.uws.ii.project.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Data
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String photoPath;
    private Integer cookingTime;
    private Float rating;
    private Boolean requireOven;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "difficulty_id", nullable = false)
    private Difficulty difficulty;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToMany
    @JoinTable(
            name = "recipes_events",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private Set<Event> events;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments;

    @ManyToMany
    @JoinTable(
            name = "recipes_ingredients",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private Set<Ingredient> ingredients;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Step> steps;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RecipeHistory> recipeHistories;

    @ManyToMany(mappedBy = "favouriteRecipes")
    private Set<User> favouriteByUsers;

    public Recipe(String name, String description, String photoPath, Integer cookingTime, Float rating, Boolean requireOven, LocalDateTime createdAt, User user, Set<Ingredient> ingredients, Category category, Difficulty difficulty, Set<Event> events) {
        this.id = null;
        this.name = name;
        this.description = description;
        this.photoPath = photoPath;
        this.cookingTime = cookingTime;
        this.rating = rating;
        this.requireOven = requireOven;
        this.createdAt = createdAt;
        this.user = user;
        this.ingredients = ingredients;
        this.category = category;
        this.difficulty = difficulty;
        this.events = events;
    }
}
