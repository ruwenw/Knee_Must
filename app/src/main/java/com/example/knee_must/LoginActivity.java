package com.example.knee_must;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
                    sharedPref.SetIsLogedIn(true);
                }
                if (!found) {
                    for (int j = 0; j < DataModel.patients.size(); j++) {
                        if (DataModel.patients.get(j).getUsername().equals(username.getText().toString()) && DataModel.patients.get(j).getPassword().equals(password.getText().toString())) {
                            Intent intent = new Intent(this, MainActivity.class);
                            startActivity(intent);
                            sharedPref.SetUsername(DataModel.patients.get(j).getUsername());
                            sharedPref.SetIsLogedIn(true);
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);

        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            item.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }

        MenuItem item;
        item = menu.getItem(4);
        item.setEnabled(false);
        item.setVisible(false);
        item = menu.getItem(1);
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
        } else if (id == R.id.action_register) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivityForResult(intent, 0);
            return true;
        } else if (id == R.id.action_GoHome) {
            if (sharedPref.IsLogedIN()) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);

            } else {
                Toast.makeText(this, "You need to login to continue", Toast.LENGTH_SHORT).show();
            }
            //finish();
            return true;
        }
        return true;

    }
}

