package com.example.knee_must;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
EditText etusername,etpassword,fname,lname,etid,etphonenumber;
TextView notfdoctor;
CheckBox isdoctor;
Button submit;
boolean needid=false;
SharedPreference sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPreference(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        needid=true;
        fname=findViewById(R.id.etFName);
        lname=findViewById(R.id.etLName);
        etusername=findViewById(R.id.etusername);
        etpassword=findViewById(R.id.etpassword);
        etphonenumber=findViewById(R.id.etphonenumber);
        etid=findViewById(R.id.etid);
        notfdoctor=findViewById(R.id.notfdoctor);
        isdoctor=findViewById(R.id.cbndoctor);
        isdoctor.setOnClickListener(this);
        submit=findViewById(R.id.btnsubmit);
        submit.setOnClickListener(this);




    }

    @Override
    public void onClick(View view) {
        if(view==isdoctor)
        {
            notfdoctor.setText("*Not for Doctors");
            needid=false;

        }
        if(view==submit)
        {
            if(needid==true&& etid.getText().toString().equals("")){
                Toast.makeText(this, "You need to enter id", Toast.LENGTH_SHORT).show();
                return;}
            else if (etusername.getText().toString().equals("") || etpassword.getText().toString().equals("")|| fname.getText().toString().equals("")|| lname.getText().toString().equals("")) {
                    Toast.makeText(this, "fill in all boxes", Toast.LENGTH_SHORT).show();
                    return;
                }

            else if(needid){
                    for (int i = 0; i < DataModel.patients.size(); i++) {
                        if (DataModel.patients.get(i).getUsername().equals(etusername.getText().toString())) {
                            Toast.makeText(this, "username is taken", Toast.LENGTH_SHORT).show();
                            return;
                           //tempU = i;
                        }

                    }
                }
                else {
                    for (int i = 0; i < DataModel.doctors.size(); i++) {
                        if (DataModel.doctors.get(i).getUsername().equals(etusername.getText().toString())) {
                            Toast.makeText(this, "username is taken", Toast.LENGTH_SHORT).show();
                            return;
                            //tempU = i;
                        }

                    }

                }
                if(!needid){
                    ArrayList<Integer> a1=new ArrayList<Integer>();
                    a1.add(-1);
                    Doctor d=new Doctor(etusername.getText().toString(), etpassword.getText().toString(),
                            fname.getText().toString(),a1, lname.getText().toString(),etphonenumber.getText().toString());
                    DataModel.doctors.add(d);
                    DataModel.saveDoctors();
                    sharedPref.SetUsername(etusername.getText().toString());
                    finish();

                }
                else
                {
                    ArrayList<Integer> a2=new ArrayList<Integer>();
                    a2.add(-1);
                    ArrayList<String> a3=new ArrayList<>();
                    a3.add(null);
                    Patient p=new Patient(etusername.getText().toString(), etpassword.getText().toString(),etid.getText().toString(),
                            fname.getText().toString(), lname.getText().toString(),a2,a3);
                    DataModel.patients.add(p);
                    DataModel.savePatients();
                    sharedPref.SetUsername(etusername.getText().toString());
                    finish();

                }

            }



        }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);

        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);

        }
        MenuItem item;
        item = menu.getItem(2);
        item.setEnabled(false);
        item.setVisible(false);
        item = menu.getItem(4);
        item.setEnabled(false);
        item.setVisible(false);
        item = menu.getItem(6);
        item.setEnabled(false);
        item.setVisible(false);

        if (sharedPref.GetUsername().equals("")) {
            item = menu.getItem(0);
            item.setEnabled(false);
            item.setVisible(false);


        }
        else {
            item = menu.getItem(0);
            item.setTitle(sharedPref.GetUsername());
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_login) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, 0);
            //Toast.makeText(this,"you selected login",Toast.LENGTH_LONG).show();
            return true;

        }else if (id == R.id.action_Delete) {
            Intent intent = new Intent(this, DeleteActivity.class);
            startActivityForResult(intent, 0);
            return true;
        } else if (id == R.id.action_GoHome) {
            if(sharedPref.IsLogedIN()){
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);

            }else {
                Toast.makeText(this, "You need to login to continue", Toast.LENGTH_SHORT).show();
            }
            //finish();
            return true;
        }
        /*} else if (id == R.id.action_exit) {
            builder.setMessage("Do you want to logout?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                            sharedPref.SetUsername("YouRGuest");
                            Toast.makeText(getApplicationContext(), "You logged out",
                                    Toast.LENGTH_SHORT).show();
                            restartapp();
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

        return true;
    }
    }
