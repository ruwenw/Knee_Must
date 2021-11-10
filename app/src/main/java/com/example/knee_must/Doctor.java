package com.example.knee_must;

import java.util.ArrayList;

public class Doctor {
    private String username;
    private String password;
    private ArrayList<Integer> patients;

    public Doctor() {
    }

    public Doctor(String username, String password, ArrayList<Integer> patients) {
        this.username = username;
        this.password = password;
        this.patients = patients;
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

    public ArrayList<Integer> getPatients() {
        return patients;
    }

    public void setPatients(ArrayList<Integer> patients) {
        this.patients = patients;
    }
    public void addPatient(int patientnum){
        this.patients.add(patientnum);
    }
}
