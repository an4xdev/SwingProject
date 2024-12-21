package edu.uws.ii.project.services.comments;

import edu.uws.ii.project.Repositories.CommentRepository;
import edu.uws.ii.project.domain.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService implements ICommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Comment> findAllByRecipeId(Long recipeId) {
        return commentRepository.findAllByRecipe_Id(recipeId);
    }
}
