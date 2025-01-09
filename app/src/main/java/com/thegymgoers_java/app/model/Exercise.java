package com.thegymgoers_java.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Field;

public class Exercise {


    @JsonProperty("exerciseName")
    private String exerciseName;

    @JsonProperty("sets")
    private int sets;

    @JsonProperty("reps")
    private int reps;

    @JsonProperty("weight")
    private int weight;

    @JsonProperty("time")
    private int time;
}
