package com.example.knee_must;

import java.util.ArrayList;

public class  Patient extends Person{
    private String id;
    private ArrayList<Integer> exercises;
    private ArrayList<String> feedback;

    public Patient(){
        super();


    }

    public Patient(String username, String password, String id,String fname,String lname,ArrayList<Integer> exercises,ArrayList<String> feedbacks) {
        super(username, password, fname, lname);
        this.id = id;
        this.exercises = exercises;
        this.feedback=feedbacks;

    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getFeedback() {
        return feedback;
    }

    //public void setFeedback(String feedback) {
       //this.feedback = feedback;
    //}

    public ArrayList<Integer> getExercises() {
        return exercises;
    }
    public void setExercisesi(int index,int x) {
        this.exercises.set(index,x);
    }
    public void setExercises(ArrayList<Integer> exercises) {
        this.exercises = exercises;
    }
    //public void addExercises(int exnumber){
        //this.exercises.add(exnumber);
    //}
}
