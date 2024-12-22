package edu.uws.ii.project.services.categories;

import edu.uws.ii.project.Repositories.CategoryRepository;
import edu.uws.ii.project.Repositories.RecipeRepository;
import edu.uws.ii.project.domain.Category;
import edu.uws.ii.project.dtos.ManageDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;

    public CategoryService(CategoryRepository categoryRepository, RecipeRepository recipeRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Long count() {
        return categoryRepository.count();
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public List<ManageDTO> findManageDTO() {
        var categories = categoryRepository.findAll();
        return categories.stream()
                .map(category ->
                        new ManageDTO(
                                category.getId(),
                                category.getName(),
                                "category",
                                recipeRepository.countByCategory(category)
                        )
                )
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public void update(Long id, String name) {
        var category = categoryRepository.findById(id).orElse(null);
        if (category != null) {
            category.setName(name);
            categoryRepository.save(category);
        }
    }

    @Override
    public void save(String name) {
        categoryRepository.save(new Category(name));
    }

}
