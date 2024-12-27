package edu.uws.ii.project.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class Time implements Serializable {
    private Integer prepareTime;
    private Integer cookTime;
    private Integer totalTime;

    public Time() {
        prepareTime = 0;
        cookTime = 0;
        totalTime = 0;
    }

    public Time(Integer prepareTime, Integer cookTime, Integer totalTime) {
        this.prepareTime = prepareTime;
        this.cookTime = cookTime;
        this.totalTime = totalTime;
    }


}