package com.example.knee_must;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;

public class DoctorMainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
ListView lvpatients;
Button addPatient,submitaddPatient;
EditText patientId;
ArrayList<String> arypatientlist;
    SharedPreference sharedPref;
    MyListAdapter adapter;
    AlertDialog.Builder builder;
    Dialog patientDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_main);
        sharedPref = new SharedPreference(this);
        ArrayList<String> temp = new ArrayList<>();
        if( DataModel.doctors.get(sharedPref.GetFirebaseNum()).getPatients().isEmpty()){
            temp.add("No Patients yet");


        }else{
            for (int i = 0 ; i < DataModel.doctors.get(sharedPref.GetFirebaseNum()).getPatients().size();i++)
            {
                String fname=DataModel.patients.get(i).getFname();
                temp.add(fname);
            }

       }


        //}
        /*temp.add("No Patients yet");*/

        lvpatients = findViewById(R.id.lvpatients);

        adapter=new MyListAdapter(this,temp);
        lvpatients.setAdapter(adapter);
        lvpatients.setOnItemClickListener(this);
        addPatient=findViewById(R.id.addPatient);
        addPatient.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view==addPatient)
        {
            OpenAddPatientDialog();

            /*builder.setMessage("Do you want to add Exercise?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            sharedPref.SetUsername("");
                            sharedPref.Clear();
                            Toast.makeText(getApplicationContext(), "You logged out",
                                    Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(i);
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //  Action for 'NO' Button
                            dialog.cancel();
                            Toast.makeText(getApplicationContext(), "You canceled the logout",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.setTitle("Logout");
            alert.show();*/
        }

    }
    public void OpenAddPatientDialog() {
        patientDialog = new Dialog(this);
        patientDialog.setContentView(R.layout.custom_patient_add_dialog);
        patientDialog.setTitle("Add Patient");

        patientDialog.setCancelable(true);

        patientId=  patientDialog.findViewById(R.id.patientId);

        submitaddPatient = patientDialog.findViewById(R.id.submitaddPatient);


        submitaddPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v ==  submitaddPatient)
                {
                    /*if (DataModel.exercises.get(getIntent().getIntExtra("WE", 0)).getExercisesList() != null)
                    {
                        DataModel.muscles.get(getIntent().getIntExtra("WE", 0)).addExercise(
                                new Exercise(nameOfExercise.getText().toString(),
                                        sharedPref.GetUsername(),
                                        descriptionOfExercise.getText().toString(),
                                        null, null, null, "false"));
                    }
                    else
                    {
                        ArrayList<Exercise> temp = new ArrayList<>();
                        temp.add(new Exercise(nameOfExercise.getText().toString(),
                                sharedPref.GetUsername(),
                                descriptionOfExercise.getText().toString(),
                                null, null, null, "false"));
                        DataModel.muscles.get(getIntent().getIntExtra("WE", 0)).setExercisesList(temp);
                    }*/
                    for (int i = 0 ; i < DataModel.patients.size();i++)
                    {
                        if(DataModel.patients.get(i).getId().equals(patientId.getText().toString()))
                        {
                            DataModel.doctors.get(sharedPref.GetFirebaseNum()).addPatient(i);
                            DataModel.saveDoctors();
                            patientDialog.dismiss();
                            restartapp();
                        }
                    }


                }
            }
        });

        patientDialog.show();

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent1 = new Intent(this, DoctorsPatientActivity.class);
        intent1.putExtra("Patient", i);
        startActivity(intent1);
        finish();
    }
    void restartapp() {
        Intent i = new Intent(getApplicationContext(), DoctorMainActivity.class);
        //i.putExtra("WE", getIntent().getIntExtra("WE", 0));
        startActivity(i);
        finish();
    }
}