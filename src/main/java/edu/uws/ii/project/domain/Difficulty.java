package edu.uws.ii.project.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@Entity
@Data
public class Difficulty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer level;

    @OneToMany(mappedBy = "difficulty")
    private List<Recipe> recipeDiff = new ArrayList<>();

    public Difficulty(String name, Integer level) {
        this.name = name;
        this.level = level;
    }
}
