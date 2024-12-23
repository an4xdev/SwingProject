package edu.uws.ii.project.services.recipes;

import edu.uws.ii.project.Repositories.CategoryRepository;
import edu.uws.ii.project.Repositories.DifficultyRepository;
import edu.uws.ii.project.Repositories.EventRepository;
import edu.uws.ii.project.Repositories.RecipeRepository;
import edu.uws.ii.project.domain.Recipe;
import edu.uws.ii.project.dtos.SearchFormDTO;
import edu.uws.ii.project.specifications.RecipeSearchSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeService implements IRecipeService {
    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final DifficultyRepository difficultyRepository;
    private final EventRepository eventRepository;

    public RecipeService(RecipeRepository recipeRepository, CategoryRepository categoryRepository, DifficultyRepository difficultyRepository, EventRepository eventRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.difficultyRepository = difficultyRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public Optional<Recipe> getRecipeById(Long id) {
        return recipeRepository.findById(id);
    }

    @Override
    public Page<Recipe> getPage(Pageable pageable) {
        Pageable sortedByDate = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by("createdAt").descending());
        return recipeRepository.findAll(sortedByDate);
    }

    @Override
    public void deleteRecipeById(Long id) {
        recipeRepository.deleteById(id);
    }

    @Override
    public List<Recipe> findByCategory(String category) {
        var cat = categoryRepository.findByName(category);
        return recipeRepository.findAllByCategory(cat);
    }

    @Override
    public List<Recipe> findByDifficulty(String difficulty) {
        var diff = difficultyRepository.findByName(difficulty);
        return recipeRepository.findAllByDifficulty(diff);
    }

    @Override
    public List<Recipe> findByEvent(String event) {
        var ev = eventRepository.findByName(event);
        return recipeRepository.findAllByEvents(new HashSet<>(List.of(ev)));
    }

    @Override
    public List<Recipe> findBySpecification(SearchFormDTO searchFormDTO) {

        Specification<Recipe> spec = Specification
                .where(RecipeSearchSpecification
                        .findByPhrase(
                                searchFormDTO.getPhrase()
                        )
                ).and(RecipeSearchSpecification
                        .findByEventsIds(
                                searchFormDTO.getEventIds()
                        )
                );

        if (searchFormDTO.getDifficultyId() != 0) {
            spec = spec.and(RecipeSearchSpecification
                    .findByDifficultyId(
                            searchFormDTO.getDifficultyId()
                    )
            );
        }

        if (searchFormDTO.getCategoryId() != 0) {
            spec = spec.and(RecipeSearchSpecification
                    .findByCategoryId(
                            searchFormDTO.getCategoryId()
                    )
            );
        }

        Sort sort = Sort.unsorted();
        if (searchFormDTO.getSortType() != null) {
            sort = switch (searchFormDTO.getSortType().intValue()) {
                case 1 -> Sort.by(Sort.Direction.DESC, "createdAt");
                case 2 -> Sort.by(Sort.Direction.ASC, "createdAt");
                case 3 -> Sort.by(Sort.Direction.ASC, "name");
                case 4 -> Sort.by(Sort.Direction.ASC, "difficulty.id");
                default -> sort;
            };
        }
        return recipeRepository.findAll(spec, sort).stream().distinct().collect(Collectors.toList());
    }
}

