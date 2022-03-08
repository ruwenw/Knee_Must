package com.example.knee_must;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DoctorsPatientActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
TextView tvFeeback;
ListView lvexr;
EditText exerName,exerDescription,name;
    ArrayList<String> aryexlist;
    MyListAdapter adapter;
Button removePatient,addExer,addnewExer,submitaddExer,submitaddnewExer;
    SharedPreference sharedPref;
    AlertDialog.Builder builder;
    Dialog exerDialog,newexerDialog;
    //int patientnum=DataModel.doctors.get(sharedPref.GetFirebaseNum()).getPatients().get(getIntent().getIntExtra("Patient",0));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_patient);
        sharedPref = new SharedPreference(this);
        builder = new AlertDialog.Builder(this);
        tvFeeback=findViewById(R.id.tvfeedback);
        removePatient=findViewById(R.id.removePatient);
        addExer=findViewById(R.id.addPaExer);
        ArrayList<String> temp = new ArrayList<>();
        lvexr = findViewById(R.id.lvexer);
        if(DataModel.patients.get(getIntent().getIntExtra("Patient",0)).getExercises().get(0)==-1)
        {
            temp.add("No Exercises yet");
            adapter=new MyListAdapter(this,temp);
            lvexr.setAdapter(adapter);
        }else{
            for (int i = 0 ; i < DataModel.patients.get(getIntent().getIntExtra("Patient",0)).getExercises().size();i++)
            {
                temp.add(DataModel.exercises.get(DataModel.patients.get(getIntent().getIntExtra("Patient",0)).getExercises().get(i)).getName());
            }
            adapter=new MyListAdapter(this,temp);
            lvexr.setAdapter(adapter);
            lvexr.setOnItemClickListener(this);
        }


        //tvFeeback.setText(DataModel.patients.get(DataModel.doctors.get(sharedPref.GetFirebaseNum()).getPatient(getIntent().getIntExtra("Patient",0))).getFeedback());
        removePatient.setOnClickListener(this);
        addExer.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view==removePatient)
        {
            builder.setMessage("Do you want to remove the Patient")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            DataModel.doctors.get(sharedPref.GetFirebaseNum()).getPatients().remove(getIntent().getIntExtra("Patient",0));
                            DataModel.saveDoctors();
                            Toast.makeText(getApplicationContext(), "Patient removed",
                                    Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), DoctorMainActivity.class);
                            startActivity(i);
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //  Action for 'NO' Button
                            dialog.cancel();
                            Toast.makeText(getApplicationContext(), "You canceled ",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.setTitle("Remove patient");
            alert.show();
        }
        if(view==addExer)
        {
            OpenAddExerDialog();

        }


    }
    public void OpenAddExerDialog() {
        exerDialog = new Dialog(this);
        exerDialog.setContentView(R.layout.custom_exer_add_dialog);
        //exerDialog.setTitle("Add Exercise");

        exerDialog.setCancelable(true);
        addnewExer=exerDialog.findViewById(R.id.addnewExer);
        addnewExer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view==addnewExer) {
                    OpenAddNewExerDialog();
                }
            }
        });

        name= exerDialog.findViewById(R.id.Name);
        submitaddExer = exerDialog.findViewById(R.id.submitaddExer);
        submitaddExer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view ==  submitaddExer)
                {
                    for(int i=0;i<DataModel.exercises.size();i++){
                        if(DataModel.exercises.get(i).getName().equals(name.getText().toString())){
                            if(DataModel.patients.get(DataModel.doctors.get(sharedPref.GetFirebaseNum()).getPatient(getIntent().getIntExtra("Patient",0))).getExercises().get(0)==-1)
                            {
                                DataModel.patients.get(DataModel.doctors.get(sharedPref.GetFirebaseNum()).getPatient(getIntent().getIntExtra("Patient",0))).getExercises().set(0,i);
                            }else{
                                DataModel.patients.get(DataModel.doctors.get(sharedPref.GetFirebaseNum()).getPatient(getIntent().getIntExtra("Patient",0))).getExercises().add(i);
                            }
                            DataModel.savePatients();
                            exerDialog.dismiss();
                            restartapp();
                        }
                    }



                }
            }
        });

        exerDialog.show();
    }
    public void OpenAddNewExerDialog(){
        newexerDialog = new Dialog(this);
        newexerDialog.setContentView(R.layout.custom_new_exer_dialog);
        newexerDialog.setTitle("Add new Exercise");

        newexerDialog.setCancelable(true);
        exerName= newexerDialog.findViewById(R.id.exerName);
        exerDescription = newexerDialog.findViewById(R.id.exerDescription);
        submitaddnewExer = newexerDialog.findViewById(R.id.submitaddnewExer);


        submitaddnewExer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v ==  submitaddnewExer)
                {
                    DataModel.exercises.add(new Exercise(exerName.getText().toString(),
                                    exerDescription.getText().toString()));
                    DataModel.saveExercieses();
                    newexerDialog.dismiss();
                    restartapp();
                }
            }
        });
        newexerDialog.show();
    }
    void restartapp() {
        Intent i = new Intent(this, DoctorsPatientActivity.class);
        //i.putExtra("WE", getIntent().getIntExtra("WE", 0));
        startActivity(i);
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent1 = new Intent(this, ExerciseActivity.class);
        intent1.putExtra("EXSIZE", i);
        startActivity(intent1);
        finish();
    }
}