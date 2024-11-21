package edu.uws.ii.project.Repositories;

import edu.uws.ii.project.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
