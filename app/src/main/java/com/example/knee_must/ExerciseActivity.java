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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ExerciseActivity extends AppCompatActivity implements IView, View.OnClickListener{
    TextView tvexname,tvlink,tvmessage,tvlevel;
    EditText etfeedback;
    Button finish,finishdialog;
    RadioGroup radiogroup;
    RadioButton radiobutton1;
    SharedPreference sharedPref;
    AlertDialog.Builder builder;
    Dialog feedbackDialog;
    private IPrestenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPreference(this);
        builder = new AlertDialog.Builder(this);
        super.onCreate(savedInstanceState);
        this.presenter=new Presenter(this);
        setContentView(R.layout.activity_exercise);
        tvlevel=findViewById(R.id.tvlevel);
        finish=findViewById(R.id.finishex);
        tvexname = findViewById(R.id.exname);
        tvlink=findViewById(R.id.tvlink);
        tvmessage=findViewById(R.id.tvmessage);
        int level1=DataModel.patients.get(sharedPref.GetFirebaseNum()).getExercises().get(getIntent().getIntExtra("EXSIZE",0))%10;
        int exercise=DataModel.patients.get(sharedPref.GetFirebaseNum()).getExercises().get(getIntent().getIntExtra("EXSIZE",0))/10-1;
        tvexname.setText(DataModel.exercises.get(exercise).getName());
        tvlevel.setText("Level: "+(level1+1)+"  Ankle: "+DataModel.exercises.get(exercise).getLevels().get(level1));
        tvlink.setText(DataModel.exercises.get(exercise).getDescription());
        finish.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {

        if(view==finish)
        {
            OpenFeedbackdialog();
        }


    }
    public void OpenFeedbackdialog(){
        feedbackDialog=new Dialog(this);
        feedbackDialog.setContentView(R.layout.custom_feedback_dialog);
        feedbackDialog.setCancelable(true);
        etfeedback=feedbackDialog.findViewById(R.id.etfeedback);
        finishdialog=feedbackDialog.findViewById(R.id.finishexdialog);
        finishdialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view==finishdialog)
                {
                    DataModel.patients.get(sharedPref.GetFirebaseNum()).getFeedback().set(getIntent().getIntExtra("EXSIZE",0), etfeedback.getText().toString());
                    DataModel.savePatients();
                    feedbackDialog.dismiss();
                    restartapp();
                }
            }
        });
        feedbackDialog.show();



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        MenuItem item;
        for(int i=0;i<menu.size();i++)
        {
             item= menu.getItem(i);

        }

        item = menu.getItem(0);
        item.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
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
            if(sharedPref.IsDoctor())
            {
                Intent intent = new Intent(this, DoctorsPatientActivity.class);
                startActivityForResult(intent, 0);
            }else{
                Intent intent = new Intent(this, ExercisesListActivity.class);
                startActivity(intent);
            }
         }else if (id == R.id.action_SetTimer) {
             Intent intent = new Intent(this, NotificationActivity.class);
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

    @Override
    public void DatatoPtresenter(double angle) {
        this.presenter.ViewtoModel(angle);
    }

    @Override
    public void Displaymessage(String st) {
        tvmessage.setText(st);
    }
    void restartapp() {
        Intent i = new Intent(this,ExercisesListActivity.class);
        //i.putExtra("WE", getIntent().getIntExtra("WE", 0));
        startActivity(i);
        finish();
    }


}