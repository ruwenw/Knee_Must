package com.example.knee_must;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DataModel {
    static public ArrayList<Doctor> doctors = new ArrayList<>();
    static public ArrayList<Patient> patients = new ArrayList<>();
    static public ArrayList<Exercise> exercises = new ArrayList<>();


    public static void saveDoctors() {
        FirebaseDatabase.getInstance("https://knee-must-default-rtdb.europe-west1.firebasedatabase.app/").getReference("doctor").setValue(DataModel.doctors);
    }
    public static void savePatients() {
        FirebaseDatabase.getInstance("https://knee-must-default-rtdb.europe-west1.firebasedatabase.app/").getReference("patients").setValue(DataModel.patients);
    }
    public static void saveExercieses() {
        FirebaseDatabase.getInstance("https://knee-must-default-rtdb.europe-west1.firebasedatabase.app/").getReference("exercises").setValue(DataModel.exercises);
    }
}
