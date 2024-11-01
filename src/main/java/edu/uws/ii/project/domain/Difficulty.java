package edu.uws.ii.project.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Difficulty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer level;

    @OneToMany(mappedBy = "difficulty")
    private List<Recipe> recipeDiff = new ArrayList<>();

    public Difficulty() {
    }

    public Difficulty(String name, Integer level) {
        this.name = name;
        this.level = level;
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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public List<Recipe> getRecipeDiff() {
        return recipeDiff;
    }

    public void setRecipeDiff(List<Recipe> recipeDiff) {
        this.recipeDiff = recipeDiff;
    }
}
