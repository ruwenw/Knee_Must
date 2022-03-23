package com.example.knee_must;

import java.util.ArrayList;

public class Doctor extends Person {

    private ArrayList<Integer> patients;
    private String phonenumber;

    public Doctor() {
        super();
    }

    public Doctor(String username, String password, String fname,ArrayList<Integer> patients,String lname,String phonenumber) {
        super(username,password,fname,lname);
        this.patients=patients;
        this.phonenumber=phonenumber;
       // new ArrayList<Integer>(new Integer(-1))
        //this.patients.set(0,-1);

    }
 


    public ArrayList<Integer> getPatients() {
        return patients;
    }
    public int getPatient(int i) {
        return this.patients.get(i);
    }
    public String getPhonenumber() {
        return this.phonenumber;
    }

    public void setPatients(ArrayList<Integer> patients) {
        this.patients = patients;
    }
    public void addPatient(int patientnum){
        if(this.patients==null)
        {
            this.patients=new ArrayList<>();
        }
        this.patients.add(patientnum);
    }
}
