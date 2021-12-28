package com.example.knee_must;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DoctorsPatientActivity extends AppCompatActivity implements View.OnClickListener{
TextView tvFeeback;
Button removePatient;
    SharedPreference sharedPref;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_patient);
        sharedPref = new SharedPreference(this);
        builder = new AlertDialog.Builder(this);
        tvFeeback=findViewById(R.id.tvfeedback);
        removePatient=findViewById(R.id.removePatient);
        tvFeeback.setText(DataModel.patients.get(getIntent().getIntExtra("Patient",0)).getFeedback());
        removePatient.setOnClickListener(this);
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


    }
}