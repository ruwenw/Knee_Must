package com.example.knee_must;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyListAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final ArrayList<String> maintitle;
    public MyListAdapter(Activity context, ArrayList<String> maintitle) {
        super(context, R.layout.mylistadapter, maintitle);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.maintitle = maintitle;
//hc


    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.mylistadapter, null, true);

        TextView titleText = rowView.findViewById(R.id.title);

        titleText.setText(maintitle.get(position));

        return rowView;

    }
}
