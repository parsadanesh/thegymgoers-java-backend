package com.thegymgoers_java.app.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

public class Workout {

    // Fields

    @Field("exercises")
    private List<Exercise> exercises;

    @CreatedDate
    @Field("date_created")
    private Date dateCreated;

    // Getters and setters

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public Date getDataCreated() {
        return dateCreated;
    }

    public void setDataCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
