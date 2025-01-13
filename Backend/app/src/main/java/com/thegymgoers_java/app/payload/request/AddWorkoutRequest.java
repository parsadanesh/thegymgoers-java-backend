package com.thegymgoers_java.app.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thegymgoers_java.app.model.Exercise;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Field;


import java.util.Date;
import java.util.List;

public class AddWorkoutRequest {


    @JsonProperty("exercises")
    private List<Exercise> exercises;


    @JsonProperty("date_created")
    private Date dateCreated;

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
