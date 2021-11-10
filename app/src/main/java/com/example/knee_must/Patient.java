package com.example.knee_must;

import java.util.ArrayList;

public class Patient {
    private String username;
    private String password;
    private String id;
    private ArrayList<Integer> exercises;

    public Patient() {
    }

    public Patient(String username, String password, String id, ArrayList<Integer> exercises) {
        this.username = username;
        this.password = password;
        this.id = id;
        this.exercises = exercises;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Integer> getExercises() {
        return exercises;
    }

    public void setExercises(ArrayList<Integer> exercises) {
        this.exercises = exercises;
    }
    public void addExercises(int exnumber){
        this.exercises.add(exnumber);
    }
}
