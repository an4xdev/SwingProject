package edu.uws.ii.project.Repositories;

import edu.uws.ii.project.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
