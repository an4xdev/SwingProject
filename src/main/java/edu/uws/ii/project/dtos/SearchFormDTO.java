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
    @NotEmpty
    private String phrase;

    @Size(min = 1)
    private List<Long> eventIds;

    @Min(value = 0)
    private Long categoryId;

    @Min(value = 0)
    private Long difficultyId;

    @Min(value = 1)
    private Long sortType;
}
