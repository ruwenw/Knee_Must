package com.example.knee_must;

import java.util.ArrayList;

public class Patient extends Person{
    private String id;
    private ArrayList<Exercise> exercises;
    private String feedback;

    public Patient(){
        super();


    }

    public Patient(String username, String password, String id, ArrayList<Exercise> exercises,String fname,String lname) {
        super(username, password, fname, lname);
        this.id = id;
        this.exercises = DataModel.exercises;
        this.feedback=null;

    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
    /*
    public ArrayList<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(ArrayList<Integer> exercises) {
        this.exercises = exercises;
    }
    public void addExercises(int exnumber){
        this.exercises.add(exnumber);
    }*/
}
