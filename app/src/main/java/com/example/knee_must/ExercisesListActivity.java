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
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.AbstractSet;
import java.util.ArrayList;

public class  ExercisesListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener ,View.OnClickListener{
    ListView lvexr;
    EditText exerName,exerDescription;
    Button addExer,submitaddExer;
    ArrayList<String> aryexlist;
    MyListAdapter adapter;
    SharedPreference sharedPref;
    AlertDialog.Builder builder;
    Dialog exerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPreference(this);
        builder = new AlertDialog.Builder(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises_list);
        ArrayList<String> temp = new ArrayList<>();
        for (int i = 0 ; i < DataModel.exercises.size();i++)
        {
            temp.add(DataModel.exercises.get(i).getName());
        }
        //temp.add

        adapter=new MyListAdapter(this,temp);
        lvexr = findViewById(R.id.lvex);
        addExer=findViewById(R.id.addExer);

        lvexr.setAdapter(adapter);
        lvexr.setOnItemClickListener(this);
        addExer.setOnClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent1 = new Intent(this, ExerciseActivity.class);
        intent1.putExtra("EXSIZE", i);
        startActivity(intent1);
        finish();

    }
    @Override
    public void onClick(View view) {
        if(view==addExer)
        {
            OpenAddExerDialog();

            /*builder.setMessage("Do you want to add Exercise?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
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
            alert.show();*/
        }

    }
    public void OpenAddExerDialog() {
        exerDialog = new Dialog(this);
        exerDialog.setContentView(R.layout.custom_exer_add_dialog);
        exerDialog.setTitle("Add Exercise");

        exerDialog.setCancelable(true);

        exerName= exerDialog.findViewById(R.id.exerName);
        exerDescription = exerDialog.findViewById(R.id.exerDescription);
        submitaddExer = exerDialog.findViewById(R.id.submitaddExer);


        submitaddExer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v ==  submitaddExer)
                {
                    /*if (DataModel.exercises.get(getIntent().getIntExtra("WE", 0)).getExercisesList() != null)
                    {
                        DataModel.muscles.get(getIntent().getIntExtra("WE", 0)).addExercise(
                                new Exercise(nameOfExercise.getText().toString(),
                                        sharedPref.GetUsername(),
                                        descriptionOfExercise.getText().toString(),
                                        null, null, null, "false"));
                    }
                    else
                    {
                        ArrayList<Exercise> temp = new ArrayList<>();
                        temp.add(new Exercise(nameOfExercise.getText().toString(),
                                sharedPref.GetUsername(),
                                descriptionOfExercise.getText().toString(),
                                null, null, null, "false"));
                        DataModel.muscles.get(getIntent().getIntExtra("WE", 0)).setExercisesList(temp);
                    }*/
                    DataModel.exercises.add(
                            new Exercise(exerName.getText().toString(),
                                    exerDescription.getText().toString()));
                    DataModel.saveExercieses();
                    exerDialog.dismiss();
                    restartapp();
                }
            }
        });

        exerDialog.show();
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

        if (id == R.id.action_login) {
            //Intent intent = new Intent(this, LoginActivity.class);
            //startActivityForResult(intent, 0);
            Toast.makeText(this,"You are already loged in",Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.action_register) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivityForResult(intent, 0);
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
    void restartapp() {
        Intent i = new Intent(getApplicationContext(), ExercisesListActivity.class);
        //i.putExtra("WE", getIntent().getIntExtra("WE", 0));
        startActivity(i);
        finish();
    }


}