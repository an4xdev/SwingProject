package edu.uws.ii.project.services.difficulties;

import edu.uws.ii.project.domain.Difficulty;
import edu.uws.ii.project.dtos.ManageDTO;

import java.util.List;

public interface IDifficultyService {
    List<Difficulty> findAll();

    Long count();

    List<ManageDTO> findManageDTO();

    void deleteById(Long id);

    /// Returns the difficulty with the given id or null if it does not exist.
    /// @param id The id of the difficulty to find.
    /// @return The difficulty with the given id or null if it does not exist.
    Difficulty findById(Long id);

    void update(Long id, String name);

    void save(String name);
}
