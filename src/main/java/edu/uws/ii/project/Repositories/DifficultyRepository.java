package edu.uws.ii.project.Repositories;

import edu.uws.ii.project.domain.Difficulty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DifficultyRepository extends JpaRepository<Difficulty, Long> {
}
