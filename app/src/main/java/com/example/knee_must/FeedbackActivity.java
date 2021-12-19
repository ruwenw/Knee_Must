package com.example.knee_must;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FeedbackActivity extends AppCompatActivity implements View.OnClickListener {
Button submitfeba;
EditText feedback;
    SharedPreference sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPreference(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        submitfeba=findViewById(R.id.submitfeba);
        feedback=findViewById(R.id.etfeedback);
        submitfeba.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view==submitfeba)
        {
            DataModel.patients.get(sharedPref.GetFirebaseNum()).setFeedback(feedback.getText().toString());
        }
    }
}