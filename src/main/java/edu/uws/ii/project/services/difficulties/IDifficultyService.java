package edu.uws.ii.project.services.difficulties;

import edu.uws.ii.project.domain.Difficulty;
import edu.uws.ii.project.dtos.ManageDTO;

import java.util.List;

public interface IDifficultyService {
    List<Difficulty> findAll();

    Long count();

    List<ManageDTO> findManageDTO();

    void deleteById(Long id);

    Difficulty findById(Long id);

    void update(Long id, String name);

    void save(String name);
}
