package edu.uws.ii.project.specifications;

import edu.uws.ii.project.domain.Recipe;
import edu.uws.ii.project.domain.Recipe_;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class RecipeSearchSpecification {
    public static Specification<Recipe> findByPhrase(final String phrase) {
        return (recipe, query, cb) -> {
            if (phrase != null && !phrase.isEmpty()) {
                String phraseLike = "%" + phrase.toUpperCase() + "%";
                return cb.like(cb.upper(recipe.get(Recipe_.NAME)), phraseLike);
            }
            return null;
        };
    }

    public static Specification<Recipe> findByDifficultyId(final Long difficultyId) {
        return (recipe, query, cb) -> {
            if (difficultyId != null) {
                return cb.equal(recipe.get(Recipe_.DIFFICULTY).get("id"), difficultyId);
            }
            return null;
        };
    }

    public static Specification<Recipe> findByCategoryId(final Long categoryId) {
        return (recipe, query, cb) -> {
            if (categoryId != null) {
                return cb.equal(recipe.get(Recipe_.CATEGORY).get("id"), categoryId);
            }
            return null;
        };
    }

    public static Specification<Recipe> findByEventsIds(final List<Long> eventIds) {
        return (recipe, query, cb) -> {
            if (eventIds != null && !eventIds.isEmpty()) {
                return recipe.get(Recipe_.EVENTS).get("id").in(eventIds);
            }
            return null;
        };
    }
}
