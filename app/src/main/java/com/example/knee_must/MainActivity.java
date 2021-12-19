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
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
SharedPreference sharedPref;
    AlertDialog.Builder builder;
    Button toexer,toFeedback;
    TextView test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPreference(this);
        builder = new AlertDialog.Builder(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //test=findViewById(R.id.test);
        //test.setText("2314");
        toexer=findViewById(R.id.toExercises);
        toFeedback=findViewById(R.id.toFeedback);
        toFeedback.setOnClickListener(this);
        toexer.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        if(view==toexer)
        {
            Intent intent = new Intent(this, ExercisesListActivity.class);
            startActivity(intent);
        }
        else if(view==toFeedback)
        {
            Intent intent = new Intent(this, FeedbackActivity.class);
            startActivityForResult(intent, 0);
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
        } else if (id == R.id.action_register) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivityForResult(intent, 0);
            return true;
        } else if (id == R.id.action_Delete) {
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