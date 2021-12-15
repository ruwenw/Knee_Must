package com.example.knee_must;

import java.util.ArrayList;

public class Doctor extends Person {

    private ArrayList<Integer> patients;

    public Doctor() {
        super();
    }

    public Doctor(String username, String password, ArrayList<Integer> patients,String fname,String lname) {
        super(username,password,fname,lname);
        this.patients = patients;

    }
 
   z

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
