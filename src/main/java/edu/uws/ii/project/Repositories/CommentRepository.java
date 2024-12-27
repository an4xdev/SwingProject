package edu.uws.ii.project.Repositories;

import edu.uws.ii.project.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByRecipe_Id(Long recipeId);

    void deleteByRecipe_Id(Long recipeId);
}
