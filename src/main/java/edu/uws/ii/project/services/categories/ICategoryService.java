package edu.uws.ii.project.services.categories;

import edu.uws.ii.project.domain.Category;
import edu.uws.ii.project.dtos.ManageDTO;

import java.util.List;

public interface ICategoryService {
    Long count();

    List<Category> findAll();

    List<ManageDTO> findManageDTO();

    void deleteById(Long id);

    Category findById(Long id);

    void update(Long id, String name);

    void save(String name);
}
