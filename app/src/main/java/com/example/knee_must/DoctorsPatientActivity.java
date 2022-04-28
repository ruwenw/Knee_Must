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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DoctorsPatientActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
TextView tvFeeback;
ListView lvexr,lvfeedb;
EditText exerName,exerDescription,name,etbeginner,etintermediate,etexpert,etlevel,etankle;
    ArrayList<String> aryexlist;
    MyListAdapter adapter,adapter2;
Button removePatient,addExer,addnewExer,submitaddExer,submitaddnewExer;
    SharedPreference sharedPref;
    AlertDialog.Builder builder;
    Dialog exerDialog,newexerDialog;
    int level=0;
    //int patientnum=DataModel.doctors.get(sharedPref.GetFirebaseNum()).getPatients().get(getIntent().getIntExtra("Patient",0));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_patient);
        sharedPref = new SharedPreference(this);
        builder = new AlertDialog.Builder(this);

        removePatient=findViewById(R.id.removePatient);
        addExer=findViewById(R.id.addPaExer);
        ArrayList<String> temp = new ArrayList<>();
        lvexr = findViewById(R.id.lvexer);
        lvfeedb=findViewById(R.id.lvfeedb);
        if(DataModel.patients.get(getIntent().getIntExtra("Patient",0)).getExercises().get(0)==-1)
        {
            temp.add("No Exercises yet");
            adapter=new MyListAdapter(this,temp);
            lvexr.setAdapter(adapter);
        }else{
            for (int i = 0 ; i < DataModel.patients.get(getIntent().getIntExtra("Patient",0)).getExercises().size();i++)
            {
                temp.add(DataModel.exercises.get((DataModel.patients.get(getIntent().getIntExtra("Patient",0)).getExercises().get(i))/10-1).getName());
            }
            adapter=new MyListAdapter(this,temp);
            lvexr.setAdapter(adapter);
            lvexr.setOnItemClickListener(this);
            ArrayList<String> temp2 = new ArrayList<>();
            String str=null;

            for (int i = 0 ; i < DataModel.patients.get(getIntent().getIntExtra("Patient",0)).getFeedback().size();i++){
                //if(DataModel.patients.get(getIntent().getIntExtra("Patient",0)).getFeedback().get((DataModel.patients.get(getIntent().getIntExtra("Patient",0)).getExercises().get(i))/10-1)!=null) {
                    str=DataModel.patients.get(getIntent().getIntExtra("Patient", 0)).getFeedback().get(i);
                    Integer x= Character.getNumericValue(str.charAt(0))-1;
                    str=str.substring(1);
               // DataModel.patients.get(getIntent().getIntExtra("Patient", 0)).getFeedback().get((DataModel.patients.get(getIntent().getIntExtra("Patient", 0)).getExercises().get(i)) / 10 - 1)
                    temp2.add(DataModel.exercises.get(x).getName() + ": " + str);

            }
            adapter2=new MyListAdapter(this,temp2);
            lvfeedb.setAdapter((adapter2));
        }



        //tvFeeback.setText(DataModel.patients.get(DataModel.doctors.get(sharedPref.GetFirebaseNum()).getPatient(getIntent().getIntExtra("Patient",0))).getFeedback());
        removePatient.setOnClickListener(this);
        addExer.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view==removePatient)
        {
            builder.setMessage("Do you want to remove the Patient")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            DataModel.doctors.get(sharedPref.GetFirebaseNum()).getPatients().remove(getIntent().getIntExtra("Patient",0));
                            DataModel.saveDoctors();
                            Toast.makeText(getApplicationContext(), "Patient removed",
                                    Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), DoctorMainActivity.class);
                            startActivity(i);
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //  Action for 'NO' Button
                            dialog.cancel();
                            Toast.makeText(getApplicationContext(), "You canceled ",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.setTitle("Remove patient");
            alert.show();
        }
        if(view==addExer)
        {
            OpenAddExerDialog();

        }


    }
    public void OpenAddExerDialog() {
        exerDialog = new Dialog(this);
        exerDialog.setContentView(R.layout.custom_exer_add_dialog);
        //exerDialog.setTitle("Add Exercise");

        exerDialog.setCancelable(true);
        addnewExer=exerDialog.findViewById(R.id.addnewExer);
        etlevel=exerDialog.findViewById(R.id.level);
        if(etlevel.getText().toString()=="beginner")
        {
             level=0;
        }else{
            if(etlevel.getText().toString()=="intermediate"){
                 level=1;
            }else
            {
                 level=2;
            }
        }
        //Not finished!!!
        //etankle=exerDialog.findViewById(R.id.ankle);
        addnewExer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view==addnewExer) {
                    OpenAddNewExerDialog();
                }
            }
        });

        name= exerDialog.findViewById(R.id.Name);
        submitaddExer = exerDialog.findViewById(R.id.submitaddExer);
        submitaddExer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view ==  submitaddExer)
                {
                    for(int i=0;i<DataModel.exercises.size();i++){
                        if(DataModel.exercises.get(i).getName().equals(name.getText().toString())){
                            if(DataModel.patients.get(DataModel.doctors.get(sharedPref.GetFirebaseNum()).getPatient(getIntent().getIntExtra("Patient",0))).getExercises().get(0)==-1)
                            {
                                DataModel.patients.get(DataModel.doctors.get(sharedPref.GetFirebaseNum()).getPatient(getIntent().getIntExtra("Patient",0))).getExercises().set(0,((i+1)*10)+level);
                            }else{
                                DataModel.patients.get(DataModel.doctors.get(sharedPref.GetFirebaseNum()).getPatient(getIntent().getIntExtra("Patient",0))).getExercises().add(((i+1)*10)+level);
                            }
                            DataModel.savePatients();
                            exerDialog.dismiss();
                            restartapp();
                        }
                    }



                }
            }
        });

        exerDialog.show();
    }
    public void OpenAddNewExerDialog(){
        newexerDialog = new Dialog(this);
        newexerDialog.setContentView(R.layout.custom_new_exer_dialog);
        newexerDialog.setTitle("Add new Exercise");

        newexerDialog.setCancelable(true);
        exerName= newexerDialog.findViewById(R.id.exerName);
        exerDescription = newexerDialog.findViewById(R.id.exerDescription);
        etintermediate=newexerDialog.findViewById(R.id.etintermediate);

        etbeginner=newexerDialog.findViewById(R.id.etbeginner);

        etexpert=newexerDialog.findViewById(R.id.etexpert);

        submitaddnewExer = newexerDialog.findViewById(R.id.submitaddnewExer);


        submitaddnewExer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v ==  submitaddnewExer)
                {
                    int intermediate=Integer.parseInt(etintermediate.getText().toString());
                    int beginner=Integer.parseInt(etbeginner.getText().toString());
                    int expert=Integer.parseInt(etexpert.getText().toString());
                    ArrayList<ExerciseLevel> levels=new ArrayList<ExerciseLevel>(3);
                    levels.add(0,new ExerciseLevel("beginner",beginner));
                    levels.add(1,new ExerciseLevel("intermediate",intermediate));
                    levels.add(2,new ExerciseLevel("expert",expert));
                    DataModel.exercises.add(new Exercise(exerName.getText().toString(),
                                    exerDescription.getText().toString(),levels));
                    DataModel.saveExercieses();
                    newexerDialog.dismiss();
                    restartapp();
                }
            }
        });
        newexerDialog.show();
    }
    void restartapp() {
        Intent i = new Intent(this, DoctorsPatientActivity.class);
        //i.putExtra("WE", getIntent().getIntExtra("WE", 0));
        startActivity(i);
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
       /* Intent intent1 = new Intent(this, ExerciseActivity.class);
        intent1.putExtra("EXSIZE", i);
        startActivity(intent1);
        finish();*/
        builder.setMessage("Do you want to delete the Exercise?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DataModel.patients.get(getIntent().getIntExtra("Patient",0)).getExercises().remove(i);
                        if(DataModel.patients.get(getIntent().getIntExtra("Patient",0)).getExercises().isEmpty())
                        {
                            ArrayList<Integer> a=new ArrayList<>(1);
                            a.set(0,-1);
                            DataModel.patients.get(getIntent().getIntExtra("Patient",0)).setExercises(a);
                        }
                        DataModel.savePatients();
                        DataModel.saveExercieses();
                        Toast.makeText(getApplicationContext(), "Deleted",
                                Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), DoctorsPatientActivity.class);
                        i.putExtra("Patient",getIntent().getIntExtra("Patient",0));
                        startActivity(i);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                        Toast.makeText(getApplicationContext(), "You canceled ",
                                Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog alert = builder.create();
        alert.setTitle("Delete Exercise");
        alert.show();
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
            Intent intent = new Intent(this, DoctorMainActivity.class);
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