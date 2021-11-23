package com.example.knee_must;

import java.util.ArrayList;

public class Doctor {
    private String username;
    private String password;
    private String fname;
    private String lname;
    private ArrayList<Integer> patients;

    public Doctor() {
    }

    public Doctor(String username, String password, ArrayList<Integer> patients,String fname,String lname) {
        this.username = username;
        this.password = password;
        this.patients = patients;
        this.fname=fname;
        this.lname=lname;
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

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
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
