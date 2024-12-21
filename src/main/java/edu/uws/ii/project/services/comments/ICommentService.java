package edu.uws.ii.project.services.comments;

import edu.uws.ii.project.domain.Comment;

import java.util.List;

public interface ICommentService {
    List<Comment> findAllByRecipeId(Long recipeId);
}
