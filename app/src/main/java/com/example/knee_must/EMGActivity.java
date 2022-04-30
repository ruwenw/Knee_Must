package com.example.knee_must;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class EMGActivity extends AppCompatActivity {
ListView lvemg;
MyListAdapter adapter1;
SharedPreference sharedPref;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emgactivity);
        sharedPref = new SharedPreference(this);
        builder = new AlertDialog.Builder(this);
        ArrayList<String> temp = new ArrayList<>();
        lvemg = findViewById(R.id.lvemg);
        for (int i = 0 ; i < DataModel.patients.get(getIntent().getIntExtra("Patientnum",0)).getEmg().size();i++)
        {
            temp.add(DataModel.patients.get(getIntent().getIntExtra("Patientnum",0)).getEmg().get(i).toString());
        }
        adapter1=new MyListAdapter(this,temp);
        lvemg.setAdapter(adapter1);
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
        item = menu.getItem(4);
        item.setEnabled(false);
        item.setVisible(false);
        item = menu.getItem(5);
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

        if (id == R.id.action_Back) {
            Intent intent = new Intent(this, DoctorMainActivity.class);
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