package edu.uws.ii.project.services.difficulties;

import edu.uws.ii.project.Repositories.DifficultyRepository;
import edu.uws.ii.project.domain.Difficulty;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DifficultyService implements IDifficultyService {
    private final DifficultyRepository difficultyRepository;

    public DifficultyService(DifficultyRepository difficultyRepository) {
        this.difficultyRepository = difficultyRepository;
    }

    @Override
    public List<Difficulty> findAll() {
        return difficultyRepository.findAll();
    }
}
