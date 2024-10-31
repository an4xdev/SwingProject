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
}
