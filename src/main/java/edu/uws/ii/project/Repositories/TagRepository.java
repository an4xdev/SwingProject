package edu.uws.ii.project.Repositories;

import edu.uws.ii.project.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
