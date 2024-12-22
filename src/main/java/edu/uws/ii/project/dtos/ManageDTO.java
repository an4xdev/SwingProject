package edu.uws.ii.project.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ManageDTO {
    private Long id;
    private String name;
    private String type;
    private Long recipesCount;
}
