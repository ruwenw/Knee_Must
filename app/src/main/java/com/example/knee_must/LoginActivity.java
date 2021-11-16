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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username=findViewById(R.id.etuname);
        password=findViewById(R.id.etpassw);
        btnlogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        boolean found = false;

        for (int i=0; i<DataModel.doctors.size(); i++)
        {
            if (DataModel.doctors.get(i).getUsername().equals(username))
            {
                DataModel.doctor = DataModel.doctors.get(i);
                Intent intent = new Intent(this, MainActivity.class);
                startActivityForResult(intent, 0);
                found = true;
            }
        }
        for (int i=0; i<DataModel.patients.size(); i++)
        {
            if (DataModel.patients.get(i).getUsername().equals(username))
            {
                DataModel.patient = DataModel.patients.get(i);
                Intent intent = new Intent(this, MainActivity.class);
                startActivityForResult(intent, 0);
                found = true;
                
            }
        }

        if (!found)
        {
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
        }

    }
}