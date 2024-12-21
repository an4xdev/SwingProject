package edu.uws.ii.project.Repositories;

import edu.uws.ii.project.domain.Favourite;
import edu.uws.ii.project.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavouriteRepository extends JpaRepository<Favourite, Long> {
    List<Favourite> findAllByUserId(Long userId);

    long countFavouritesByRecipeFav(Recipe recipeFav);
}
