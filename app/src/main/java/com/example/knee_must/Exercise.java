package com.example.knee_must;

public class
Exercise {
    private String name;
    private String description;

    public Exercise(String name, String descriptionOfExercise) {
        this.name = name;
        this.description = descriptionOfExercise;
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
