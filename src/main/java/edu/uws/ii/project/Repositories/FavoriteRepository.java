package edu.uws.ii.project.Repositories;

import edu.uws.ii.project.domain.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
}
