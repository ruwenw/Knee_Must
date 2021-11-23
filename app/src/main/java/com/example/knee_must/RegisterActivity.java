package com.example.knee_must;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
EditText etusername,etpassword,fname,lname,etid;
TextView notfdoctor;
CheckBox isdoctor;
Button submit;
boolean needid;
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
                    DataModel.doctors.add(new Doctor(etusername.getText().toString(), etpassword.getText().toString(),null,
                            fname.getText().toString(), lname.getText().toString()));
                    DataModel.saveDoctors();
                    sharedPref.SetUsername(etusername.getText().toString());
                    finish();

                }
                else
                {
                    DataModel.patients.add(new Patient(etusername.getText().toString(), etpassword.getText().toString(),etid.getText().toString(),null,
                            fname.getText().toString(), lname.getText().toString()));
                    DataModel.savePatients();
                    sharedPref.SetUsername(etusername.getText().toString());
                    finish();

                }

            }



        }
    }
