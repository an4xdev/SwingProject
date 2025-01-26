package edu.uws.ii.project.services.events;

import edu.uws.ii.project.domain.Event;
import edu.uws.ii.project.dtos.ManageDTO;
import jakarta.validation.constraints.Size;

import java.util.List;

public interface IEventService {
    Long count();

    List<Event> findAll();

    List<ManageDTO> findManageDTO();

    void deleteById(Long id);

    /// Returns the event with the given id or null if it does not exist.
    /// @param id The id of the event to find.
    /// @return The event with the given id or null if it does not exist.
    Event findById(Long id);

    void update(Long id, String name);

    void save(String name);

    List<Event> findAllByIds(@Size(min = 1, message = "Events must contain at least 1 event") List<Long> eventIds);
}
