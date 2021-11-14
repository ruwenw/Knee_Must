package com.example.knee_must;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoadingActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference dbDoctorsRef;
    DatabaseReference dbPatientsRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        database = FirebaseDatabase.getInstance();
        dbDoctorsRef=database.getReference("doctor");
        dbPatientsRef=database.getReference("patients");
        dbDoctorsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<ArrayList<Doctor>> t=new GenericTypeIndicator<ArrayList<Doctor>>() {
                };
                ArrayList<Doctor> fbDoctor = dataSnapshot.getValue(t);
                DataModel.doctors.clear();
                DataModel.doctors.addAll(fbDoctor);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        dbPatientsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<ArrayList<Patient>> t=new GenericTypeIndicator<ArrayList<Patient>>() {
                };
                ArrayList<Patient> fbPatients = dataSnapshot.getValue(t);
                DataModel.patients.clear();
                DataModel.patients.addAll(fbPatients);
                Intent intent =new Intent(getApplicationContext() ,MainActivity.class);
                startActivityForResult(intent,0);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}