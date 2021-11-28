package com.example.knee_must;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.AbstractSet;
import java.util.ArrayList;

public class ExercisesListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView lvexr;
    ArrayList<String> aryexlist;
    MyListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises_list);
        ArrayList<String> temp = new ArrayList<>();
        for (int i = 0 ; i < DataModel.exercises.size();i++)
        {
            temp.add(DataModel.exercises.get(i).getName());


        }
        temp.add("Exercise 1");
        adapter=new MyListAdapter(this,temp);
        lvexr = findViewById(R.id.lvex);

        lvexr.setAdapter(adapter);
        lvexr.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent1 = new Intent(this, ExerciseActivity.class);
        intent1.putExtra("ELTOE", i);
        startActivity(intent1);
        finish();

    }
}