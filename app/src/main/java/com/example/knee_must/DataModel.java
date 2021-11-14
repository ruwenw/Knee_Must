package com.example.knee_must;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DataModel {
    static public ArrayList<Doctor> doctors = new ArrayList<>();
    static public ArrayList<Patient> patients = new ArrayList<>();

    public static void saveDoctors() {
        FirebaseDatabase.getInstance().getReference("doctor").setValue(DataModel.doctors);
    }
    public static void savePatients() {
        FirebaseDatabase.getInstance().getReference("patients").setValue(DataModel.patients);
    }
}
