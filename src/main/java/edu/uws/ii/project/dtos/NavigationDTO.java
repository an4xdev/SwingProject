package edu.uws.ii.project.dtos;

import edu.uws.ii.project.domain.Category;
import edu.uws.ii.project.domain.Difficulty;
import edu.uws.ii.project.domain.Event;

import java.util.List;

public class NavigationDTO {
    private List<Category> categories;
    private List<Difficulty> difficulties;
    private List<Event> events;

    public NavigationDTO() {
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<Difficulty> getDifficulties() {
        return difficulties;
    }

    public void setDifficulties(List<Difficulty> difficulties) {
        this.difficulties = difficulties;
    }
}
