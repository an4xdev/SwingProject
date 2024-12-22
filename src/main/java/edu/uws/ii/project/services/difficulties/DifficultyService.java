package edu.uws.ii.project.services.difficulties;

import edu.uws.ii.project.Repositories.DifficultyRepository;
import edu.uws.ii.project.Repositories.RecipeRepository;
import edu.uws.ii.project.domain.Difficulty;
import edu.uws.ii.project.dtos.ManageDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DifficultyService implements IDifficultyService {
    private final DifficultyRepository difficultyRepository;
    private final RecipeRepository recipeRepository;

    public DifficultyService(DifficultyRepository difficultyRepository, RecipeRepository recipeRepository) {
        this.difficultyRepository = difficultyRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public List<Difficulty> findAll() {
        return difficultyRepository.findAll();
    }

    @Override
    public Long count() {
        return difficultyRepository.count();
    }

    @Override
    public List<ManageDTO> findManageDTO() {
        var difficulties = findAll();
        return difficulties.stream()
                .map(difficulty ->
                        new ManageDTO(
                                difficulty.getId(),
                                difficulty.getName(),
                                "difficulty",
                                recipeRepository.countByDifficulty(difficulty)
                        )
                )
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        difficultyRepository.deleteById(id);
    }

    @Override
    public Difficulty findById(Long id) {
        return difficultyRepository.findById(id).orElse(null);
    }

    @Override
    public void update(Long id, String name) {
        var difficulty = findById(id);
        if (difficulty != null) {
            difficulty.setName(name);
            difficultyRepository.save(difficulty);
        }
    }

    @Override
    public void save(String name) {
        difficultyRepository.save(new Difficulty(name));
    }
}
