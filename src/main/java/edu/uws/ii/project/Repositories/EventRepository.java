package edu.uws.ii.project.Repositories;

import edu.uws.ii.project.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
