package com.thegymgoers_java.app.model;

import org.springframework.data.mongodb.core.mapping.Field;

public class Exercise {


    @Field("exercise")
    private String exercise;

    @Field("sets")
    private int sets;

    @Field("reps")
    private int reps;

    @Field("weight")
    private int weight;

    @Field("time")
    private int time;
}
