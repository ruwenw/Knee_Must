package com.example.knee_must;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class
Exercise {
    private String name;
    private String description;
    private ArrayList<Integer> levels;

    public ArrayList<Integer> getLevels() {
        return levels;
    }

    public void setLevels(ArrayList<Integer> levels) {
        this.levels = levels;
    }

    public Exercise(String name, String descriptionOfExercise, ArrayList<Integer> levels) {
        this.name = name;
        this.description = descriptionOfExercise;
        this.levels=levels;

    }

    public Exercise() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
