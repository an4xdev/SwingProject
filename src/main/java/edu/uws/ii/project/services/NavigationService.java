package edu.uws.ii.project.services;

import edu.uws.ii.project.domain.Category;
import edu.uws.ii.project.domain.Difficulty;
import edu.uws.ii.project.domain.Event;
import edu.uws.ii.project.dtos.NavigationDTO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class NavigationService {

    @Cacheable("navigationData")
    public NavigationDTO getNavigationData() {
        NavigationDTO navigationDTO = new NavigationDTO();
        navigationDTO.setCategories(List.of(new Category("category1"), new Category("category2")));
        navigationDTO.setDifficulties(List.of(new Difficulty("difficulty1", 1), new Difficulty("difficulty2", 2)));
        navigationDTO.setEvents(List.of(new Event("event1", LocalDate.now()), new Event("event2", LocalDate.now())));
        return navigationDTO;
    }
}
