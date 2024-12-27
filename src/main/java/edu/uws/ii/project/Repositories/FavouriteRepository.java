package edu.uws.ii.project.Repositories;

import edu.uws.ii.project.domain.Favourite;
import edu.uws.ii.project.domain.Recipe;
import edu.uws.ii.project.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavouriteRepository extends JpaRepository<Favourite, Long> {
    List<Favourite> findAllByUserId(Long userId);

    long countFavouritesByRecipe(Recipe recipeFav);

    boolean existsByRecipeAndUser(Recipe recipe, User currentUser);

    void deleteByRecipeAndUser(Recipe recipe, User user);

    void deleteByRecipe_id(Long recipeId);
}
