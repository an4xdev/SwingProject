package edu.uws.ii.project.services.events;

import edu.uws.ii.project.domain.Event;
import edu.uws.ii.project.dtos.ManageDTO;

import java.util.List;

public interface IEventService {
    Long count();

    List<Event> findAll();

    List<ManageDTO> findManageDTO();

    void deleteById(Long id);

    Event findById(Long id);

    void update(Long id, String name);

    void save(String name);
}
