package com.example.knee_must;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
Button btntodelete;
Button btntoregister;
SharedPreference sharedPref;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        builder = new AlertDialog.Builder(this);
        username=findViewById(R.id.etuname);
        password=findViewById(R.id.etpassw);
        btnlogin=findViewById(R.id.btnlogin);
        btntoregister=findViewById(R.id.btntoregister);
        btntodelete=findViewById(R.id.todelete);
        sharedPref = new SharedPreference(this);
        btnlogin.setOnClickListener(this);
        btntodelete.setOnClickListener(this);
        btntoregister.setOnClickListener(this);
        if(sharedPref.IsLogedIN())
        {
            if(sharedPref.IsDoctor()){
                Intent intent = new Intent(this, DoctorMainActivity.class);
                startActivity(intent);
            }else{
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }

        }
    }

    @Override
    public void onClick(View view) {
        boolean found = false;
        if (view == btnlogin) {
            for (int i = 0; i < DataModel.doctors.size(); i++) {
                if (DataModel.doctors.get(i).getUsername().equals(username.getText().toString())) {
                    if (DataModel.doctors.get(i).getPassword().equals(password.getText().toString())) {
                        Intent intent = new Intent(this, DoctorMainActivity.class);
                        startActivity(intent);
                        found = true;
                        sharedPref.SetUsername(DataModel.doctors.get(i).getUsername());
                        sharedPref.SetFname(DataModel.doctors.get(i).getFname());
                        sharedPref.SetIsLogedIn(true);
                        sharedPref.SetIsDoctor(true);
                        sharedPref.SetfirebaseNum(i);
                    } else {
                        Toast.makeText(this, "Wrong Password", Toast.LENGTH_SHORT).show();
                    }


                }
            }
            if (!found) {
                for (int j = 0; j < DataModel.patients.size(); j++) {
                    if (DataModel.patients.get(j).getUsername().equals(username.getText().toString())) {
                        if (DataModel.patients.get(j).getPassword().equals(password.getText().toString())) {
                            Intent intent = new Intent(this, MainActivity.class);
                            startActivity(intent);
                            sharedPref.SetUsername(DataModel.patients.get(j).getUsername());
                            sharedPref.SetFname(DataModel.patients.get(j).getFname());
                            sharedPref.SetIsLogedIn(true);
                            sharedPref.SetfirebaseNum(j);
                            found = true;
                        } else {
                            Toast.makeText(this, "Wrong Password", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }
            if (!found) {
                Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
            }
        }
        if (view == btntoregister) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }
        if (view == btntodelete) {
            Intent intent = new Intent(this, DeleteActivity.class);
            startActivity(intent);
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

        item = menu.getItem(1);
        item.setEnabled(false);
        item.setVisible(false);
        item = menu.getItem(4);
        item.setEnabled(false);
        item.setVisible(false);
        item = menu.getItem(5);
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
        else {
            item = menu.getItem(0);
            item.setTitle(sharedPref.GetFname());
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
        }else if (id == R.id.action_SetTimer) {
            Intent intent = new Intent(this, NotificationActivity.class);
            startActivityForResult(intent, 0);
            return true;
        } else if (id == R.id.action_register) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivityForResult(intent, 0);
            return true;
        } else if (id == R.id.action_GoHome) {
            if (sharedPref.IsLogedIN()) {
                if(sharedPref.IsDoctor())
                {
                    Intent intent = new Intent(this, DoctorMainActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
            } else {
                Toast.makeText(this, "You need to login to continue", Toast.LENGTH_SHORT).show();
            }
            //finish();
            return true;
        } else if (id == R.id.action_logout) {
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

