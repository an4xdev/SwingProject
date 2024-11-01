package edu.uws.ii.project.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipeEvent;

    public Event() {
    }

    public Event(String name, LocalDate date) {
        this.name = name;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Recipe getRecipeEvent() {
        return recipeEvent;
    }

    public void setRecipeEvent(Recipe recipeEvent) {
        this.recipeEvent = recipeEvent;
    }
}
