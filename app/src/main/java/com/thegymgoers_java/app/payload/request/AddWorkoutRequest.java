package com.thegymgoers_java.app.payload.request;

import com.thegymgoers_java.app.model.Exercise;


import java.util.Date;
import java.util.List;

public class AddWorkoutRequest {

    private List<Exercise> exercises;

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
