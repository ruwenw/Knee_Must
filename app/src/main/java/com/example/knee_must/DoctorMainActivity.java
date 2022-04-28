package com.example.knee_must;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

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
        builder = new AlertDialog.Builder(this);
        ArrayList<String> temp = new ArrayList<>();
        lvpatients = findViewById(R.id.lvpatients);
        if(DataModel.doctors.get(sharedPref.GetFirebaseNum()).getPatient(0)==-1){
            temp.add("No Patients yet");
            //&&DataModel.doctors.get(sharedPref.GetFirebaseNum()).getPatients().size()==1)
            adapter=new MyListAdapter(this,temp);
            lvpatients.setAdapter(adapter);
        }else{
            for (int i = 0 ; i < (DataModel.doctors.get(sharedPref.GetFirebaseNum()).getPatients().size());i++)
            {
                //int patientnum=DataModel.doctors.get(sharedPref.GetFirebaseNum()).getPatients().get(i);
                //String fname=DataModel.patients.get(patientnum).getFname();
                temp.add(DataModel.patients.get(DataModel.doctors.get(sharedPref.GetFirebaseNum()).getPatient(i)).getFname());
            }
            adapter=new MyListAdapter(this,temp);
            lvpatients.setAdapter(adapter);
            lvpatients.setOnItemClickListener(this);
       }







        addPatient=findViewById(R.id.addPatient);
        addPatient.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view==addPatient)
        {
            OpenAddPatientDialog();


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
                    boolean found=false;

                    for (int i = 0 ; i < DataModel.patients.size();i++)
                    {
                        if(DataModel.patients.get(i).getId().equals(patientId.getText().toString()))
                        {
                            if(DataModel.doctors.get(sharedPref.GetFirebaseNum()).getPatients().get(0)==-1){
                                DataModel.doctors.get(sharedPref.GetFirebaseNum()).getPatients().set(0,i);
                            } else{
                                DataModel.doctors.get(sharedPref.GetFirebaseNum()).addPatient(i);
                            }
                            DataModel.saveDoctors();
                            patientDialog.dismiss();
                            found=true;
                            restartapp();
                        }
                    }



                    if(found==false)
                    {
                        Toast.makeText(getApplicationContext(),"Patient not found",Toast.LENGTH_LONG).show();

                    }



                }
            }
        });

        patientDialog.show();

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent1 = new Intent(this, DoctorsPatientActivity.class);
        intent1.putExtra("Patient",i);
        startActivity(intent1);
        finish();
    }
    void restartapp() {
        Intent i = new Intent(this, DoctorMainActivity.class);
        //i.putExtra("WE", getIntent().getIntExtra("WE", 0));
        startActivity(i);
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);

        for(int i=0;i<menu.size();i++)
        {
            MenuItem item= menu.getItem(i);
        }
        MenuItem item;
        item = menu.getItem(0);
        item.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
        item = menu.getItem(3);
        item.setEnabled(false);
        item.setVisible(false);
        item = menu.getItem(1);
        item.setEnabled(false);
        item.setVisible(false);
        item = menu.getItem(2);
        item.setEnabled(false);
        item.setVisible(false);
        item = menu.getItem(6);
        item.setEnabled(false);
        item.setVisible(false);


        if (sharedPref.GetFname().equals("")) {
            item = menu.getItem(0);
            item.setEnabled(false);
            item.setVisible(false);
        }
        item = menu.getItem(0);
        item.setTitle(sharedPref.GetFname());

        return true;

    }
//*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_login) {
            //Intent intent = new Intent(this, LoginActivity.class);
            //startActivityForResult(intent, 0);
            Toast.makeText(this,"You are already loged in",Toast.LENGTH_LONG).show();
            return true;
        }  else if (id == R.id.action_register) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivityForResult(intent, 0);
            return true;
        }
        else if (id == R.id.action_SetTimer) {
            Intent intent = new Intent(this, NotificationActivity.class);
            startActivityForResult(intent, 0);
            return true;
        }else if (id == R.id.action_Back) {
            Intent intent = new Intent(this, DoctorsPatientActivity.class);
            startActivityForResult(intent, 0);
            return true;
        }else if (id == R.id.action_Delete) {
            Intent intent = new Intent(this, DeleteActivity.class);
            startActivityForResult(intent, 0);
            return true;
        }else if (id == R.id.action_logout) {
            builder.setMessage("Do you want to logout?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            sharedPref.SetFname("");
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
            alert.show();
        }
        return true;
    }
}