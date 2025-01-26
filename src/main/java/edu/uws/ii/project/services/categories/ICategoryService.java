package edu.uws.ii.project.services.categories;

import edu.uws.ii.project.domain.Category;
import edu.uws.ii.project.dtos.ManageDTO;

import java.util.List;

public interface ICategoryService {
    Long count();

    List<Category> findAll();

    List<ManageDTO> findManageDTO();

    void deleteById(Long id);

    /// Returns the category with the given id or null if it does not exist.
    /// @param id The id of the category to find.
    /// @return The category with the given id or null if it does not exist.
    Category findById(Long id);

    void update(Long id, String name);

    void save(String name);
}
