package edu.uws.ii.project.services;

import edu.uws.ii.project.Repositories.CategoryRepository;
import edu.uws.ii.project.Repositories.DifficultyRepository;
import edu.uws.ii.project.Repositories.EventRepository;
import edu.uws.ii.project.dtos.NavigationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class NavigationService {

    private final CategoryRepository categoryRepository;
    private final DifficultyRepository difficultyRepository;
    private final EventRepository eventRepository;

    @Autowired
    public NavigationService(CategoryRepository categoryRepository, DifficultyRepository difficultyRepository, EventRepository eventRepository) {
        this.categoryRepository = categoryRepository;
        this.difficultyRepository = difficultyRepository;
        this.eventRepository = eventRepository;
    }

    @Cacheable("navigationData")
    public NavigationDTO getNavigationData() {
        NavigationDTO navigationDTO = new NavigationDTO();
        navigationDTO.setCategories(categoryRepository.findAll());
        navigationDTO.setDifficulties(difficultyRepository.findAll());
        navigationDTO.setEvents(eventRepository.findAll());
        return navigationDTO;
    }
}
