package edu.uws.ii.project.dtos;

import edu.uws.ii.project.domain.Category;
import edu.uws.ii.project.domain.Difficulty;
import edu.uws.ii.project.domain.Event;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class NavigationDTO {
    private List<Category> categories;
    private List<Difficulty> difficulties;
    private List<Event> events;

    public NavigationDTO() {
    }

}
