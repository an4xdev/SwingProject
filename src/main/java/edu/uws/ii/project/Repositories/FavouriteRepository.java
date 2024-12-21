package edu.uws.ii.project.Repositories;

import edu.uws.ii.project.domain.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavouriteRepository extends JpaRepository<Favourite, Long> {
}
