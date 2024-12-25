package edu.uws.ii.project.services.events;

import edu.uws.ii.project.Repositories.EventRepository;
import edu.uws.ii.project.Repositories.RecipeRepository;
import edu.uws.ii.project.domain.Event;
import edu.uws.ii.project.dtos.ManageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class EventService implements IEventService {

    private final EventRepository eventRepository;
    private final RecipeRepository recipeRepository;

    @Autowired
    public EventService(EventRepository eventRepository, RecipeRepository recipeRepository) {
        this.eventRepository = eventRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Long count() {
        return eventRepository.count();
    }

    @Override
    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    @Override
    public List<ManageDTO> findManageDTO() {
        var events = eventRepository.findAll();
        return events.stream()
                .map(event ->
                        new ManageDTO(
                                event.getId(),
                                event.getName(),
                                "event",
                                recipeRepository.countByEvents(new HashSet<>(List.of(event)))
                        )
                )
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        eventRepository.deleteById(id);
    }

    @Override
    public Event findById(Long id) {
        return eventRepository.findById(id).orElse(null);
    }

    @Override
    public void update(Long id, String name) {
        var event = eventRepository.findById(id).orElse(null);
        if (event != null) {
            event.setName(name);
            eventRepository.save(event);
        }
    }

    @Override
    public void save(String name) {
        eventRepository.save(new Event(name));
    }

    @Override
    public List<Event> findAllByIds(List<Long> eventIds) {
        return eventRepository.findAllById(eventIds);
    }
}
