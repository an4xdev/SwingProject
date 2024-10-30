package edu.uws.ii.project.domain;

import jakarta.persistence.*;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipeComm;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userComm;
}
