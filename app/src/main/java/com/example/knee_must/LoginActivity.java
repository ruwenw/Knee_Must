package com.example.knee_must;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
EditText username;
EditText password;
Button btnlogin;
Button btntoregister;
SharedPreference sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username=findViewById(R.id.etuname);
        password=findViewById(R.id.etpassw);
        btnlogin=findViewById(R.id.btnlogin);
        btntoregister=findViewById(R.id.btntoregister);
        sharedPref = new SharedPreference(this);
        btnlogin.setOnClickListener(this);
        btntoregister.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        boolean found = false;
        if(view==btnlogin)
        {
            for (int i=0; i<DataModel.doctors.size(); i++) {
                if (DataModel.doctors.get(i).getUsername().equals(username.getText().toString()) && DataModel.doctors.get(i).getPassword().equals(password.getText().toString())) {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    found = true;
                    sharedPref.SetUsername(DataModel.doctors.get(i).getUsername());
                }
                if (!found) {
                    for (int j = 0; j < DataModel.patients.size(); j++) {
                        if (DataModel.patients.get(j).getUsername().equals(username.getText().toString()) && DataModel.patients.get(j).getPassword().equals(password.getText().toString())) {
                            Intent intent = new Intent(this, MainActivity.class);
                            startActivity(intent);
                            sharedPref.SetUsername(DataModel.patients.get(j).getUsername());
                            found = true;
                        }
                    }
                }
                if (!found) {
                    Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
                }
            }
        }
        if(view==btntoregister){
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }

    }
}

