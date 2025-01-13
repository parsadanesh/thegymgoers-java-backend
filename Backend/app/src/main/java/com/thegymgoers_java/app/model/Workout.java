package com.thegymgoers_java.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

public class Workout {

    // Fields

    @Id
    @JsonProperty("_id")
    private String _id;
    @Field("exercises")
    @NotEmpty(message = "Workout needs valid exercises")
    private List<Exercise> exercises;

    @Field("date_created")
    private String dateCreated;

    public Workout() {
        this._id = new ObjectId().toString(); // Automatically generate _id
    }

    // Getters and setters


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public String getDataCreated() {
        return dateCreated;
    }

    public void setDataCreated(String  dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateCreated() {
        return dateCreated;
    }
}
