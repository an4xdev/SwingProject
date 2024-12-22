package edu.uws.ii.project.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SearchFormDTO {
    // TODO: make more user friendly messages and maybe move to a properties file
    @NotEmpty(message = "Phrase must not be empty.")
    private String phrase;

    @NotNull(message = "Event IDs must not be null.")
    @Size(min = 1, message = "At least one event must be selected.")
    private List<Long> eventIds;

    @Min(value = 0, message = "Category ID must be greater than or equal to 0.")
    private Long categoryId;

    @Min(value = 0, message = "Difficulty ID must be greater than or equal to 0.")
    private Long difficultyId;

    @Min(value = 1, message = "Sort Type must be greater than or equal to 1.")
    private Long sortType;
}
