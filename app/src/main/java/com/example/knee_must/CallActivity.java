package com.example.knee_must;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CallActivity extends AppCompatActivity {
     static final int REQUEST_CALL = 1;
     EditText mEditTextNumber,numname;
     Button btncalldoc,btnsavenumber,savenum,savenumnoname,btncallsaved;
    FileOutputStream out;
    FileInputStream in;
    String strout = null;
    String strin = null;
    Dialog SaveNumberDialog;
    AlertDialog.Builder builder;
    SharedPreference sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        sharedPref = new SharedPreference(this);
        builder = new AlertDialog.Builder(this);
        mEditTextNumber = findViewById(R.id.edit_text_number);
        btncalldoc=findViewById(R.id.btncalldoc);
        btnsavenumber=findViewById(R.id.btnsavenumber);
        btncallsaved=findViewById(R.id.btncallsaved);
        ImageView imageCall = findViewById(R.id.image_call);
        try {
            in=openFileInput("Numbers");
            byte[]buffer=new byte[4096];
            try {
                in.read(buffer);
                strin=new String(buffer);
                in.close();
                if(strin==null)
                    btncallsaved.setText("Empty");
                else
                    btncallsaved.setText(strin);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        imageCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall(mEditTextNumber.getText().toString());
            }
        });
        btncalldoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhoneCall(DataModel.doctors.get(0).getPhonenumber());
            }
        });
        btncallsaved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhoneCall(strin);
            }
        });
        btnsavenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                safenumber();
            }
        });


    }
    public void safenumber()
    {
        builder.setMessage("Do you want to save?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            strout = mEditTextNumber.getText().toString();
                            out = openFileOutput("Numbers",MODE_PRIVATE|MODE_APPEND);
                        }
                        catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        if(strout.length()>0)
                        {
                            try {
                                out.write(strout.getBytes(),0,strout.length());
                                out.close();
                                //Toast.makeText(this,"Your data saved to file",Toast.LENGTH_LONG).show();
                            }
                            catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        Toast.makeText(getApplicationContext(), "Saved",
                                Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), CallActivity.class);
                        startActivity(i);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                        Toast.makeText(getApplicationContext(), "You canceled saving",
                                Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog alert = builder.create();
        alert.setTitle("Save");
        alert.show();
        /*AlertDialog alert = builder.create();
        alert.setTitle("Logout");
        alert.show();
        SaveNumberDialog=new Dialog(this);
        SaveNumberDialog.setContentView(R.layout.custom_savenumber_dialog);
        SaveNumberDialog.setCancelable(true);
        numname=SaveNumberDialog.findViewById(R.id.numname);
        savenum=SaveNumberDialog.findViewById(R.id.savenum);
        savenumnoname=SaveNumberDialog.findViewById(R.id.savenumnoname);

        savenumnoname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view==savenumnoname)
                {
                    try {
                        strout = mEditTextNumber.getText().toString();
                        out = openFileOutput("Numbers",MODE_PRIVATE|MODE_APPEND);
                    }
                    catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    if(strout.length()>0)
                    {
                        try {
                            out.write(strout.getBytes(),0,strout.length());
                            out.close();
                            //Toast.makeText(this,"Your data saved to file",Toast.LENGTH_LONG).show();
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    SaveNumberDialog.dismiss();
                    restartapp();


                }
            }
        });
        savenum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view==savenumnoname)
                {
                    try {
                        strout = numname.getText().toString()+","+;
                        out = openFileOutput("Numbers",MODE_PRIVATE|MODE_APPEND);
                    }
                    catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    if(strout.length()>0)
                    {
                        try {
                            out.write(strout.getBytes(),0,strout.length());
                            out.close();
                            //Toast.makeText(this,"Your data saved to file",Toast.LENGTH_LONG).show();
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    SaveNumberDialog.dismiss();



                }
            }
        });
        SaveNumberDialog.show();
*/
    }
    void restartapp() {
        Intent i = new Intent(this,CallActivity.class);
        //i.putExtra("WE", getIntent().getIntExtra("WE", 0));
        startActivity(i);
        finish();
    }

    private void makePhoneCall(String number) {

        if (number.trim().length() > 0) {

            if (ContextCompat.checkSelfPermission(CallActivity.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(CallActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }

        } else {
            Toast.makeText(CallActivity.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall(mEditTextNumber.getText().toString());
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
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
        item =menu.getItem(3);
        item.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
        item = menu.getItem(1);
        item.setEnabled(false);
        item.setVisible(false);
        item = menu.getItem(2);
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
        }else if (id == R.id.action_Back) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivityForResult(intent, 0);
            return true;
        }else if (id == R.id.action_SetTimer) {
            Intent intent = new Intent(this, NotificationActivity.class);
            startActivityForResult(intent, 0);
            return true;
        }else if (id == R.id.action_Delete) {
            Intent intent = new Intent(this, DeleteActivity.class);
            startActivityForResult(intent, 0);
            return true;
        }else if (id == R.id.action_GoHome) {

            if(sharedPref.IsDoctor())
            {
                Intent intent = new Intent(this, DoctorMainActivity.class);
                startActivity(intent);
            }else{
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }

            //finish();
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