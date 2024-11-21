package edu.uws.ii.project.Repositories;

import edu.uws.ii.project.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
