package edu.uws.ii.project.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SearchFormDTO {
    // TODO: maybe move to a properties file?
    @NotEmpty(message = "Phrase must not be empty.")
    private String phrase;

    @Size(min = 1, message = "At least one event must be selected.")
    private List<Long> eventIds;

    @Min(value = 0, message = "If you want to search for all categories select `All`. Otherwise select a category.")
    private Long categoryId;

    @Min(value = 0, message = "If you want to search for all difficulties select `All`. Otherwise select a difficulty.")
    private Long difficultyId;

    @Min(value = 1, message = "You must specify sorting criterion.")
    private Long sortType;
}
