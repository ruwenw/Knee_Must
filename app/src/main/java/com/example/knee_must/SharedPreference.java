package com.example.knee_must;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {
    SharedPreferences mySharedPrefrences;

    public SharedPreference(Context context) {
        mySharedPrefrences = context.getSharedPreferences("filename", context.MODE_PRIVATE);
    }
    public void SetUsername(String username) {
        SharedPreferences.Editor editor = mySharedPrefrences.edit();
        editor.putString("username", username);
        editor.commit();
    }
    public void SetIsLogedIn(boolean islogedin)
    {
        SharedPreferences.Editor editor= mySharedPrefrences.edit();
        editor.putBoolean("islogedin",islogedin);
        editor.commit();
    }
    public void SetFname(String fname) {
        SharedPreferences.Editor editor = mySharedPrefrences.edit();
        editor.putString("fname", fname);
        editor.commit();
    }
    public String GetFname() {
        String fName = mySharedPrefrences.getString("fname","");
        return fName;
    }
    public void Clear()
    {
        SharedPreferences.Editor editor = mySharedPrefrences.edit();
        editor.clear();
        editor.commit();
    }
    public boolean IsLogedIN()
    {
        boolean is=mySharedPrefrences.getBoolean("islogedin",false);
        return is;

    }
    public void setTimer(String time)
    {
        SharedPreferences.Editor editor = mySharedPrefrences.edit();
        editor.putString("Timertime",time);
        editor.commit();
    }
    public String getTimer()
    {
        String timer=mySharedPrefrences.getString("Timertime","null");
        return timer;
    }


    public String GetUsername() {
        String user = mySharedPrefrences.getString("username","");
        return user;
    }
    public void SetfirebaseNum(int i) {
        SharedPreferences.Editor editor = mySharedPrefrences.edit();
        editor.putInt("firebasenum",i);
        editor.commit();
    }
    public int GetFirebaseNum() {
        int i= mySharedPrefrences.getInt("firebasenum",0);
        return i;
    }
    public void SetIsDoctor(boolean isDoctor)
    {
        SharedPreferences.Editor editor= mySharedPrefrences.edit();
        editor.putBoolean("isDoctor",isDoctor);
        editor.commit();
    }
    public boolean IsDoctor()
    {
        boolean is=mySharedPrefrences.getBoolean("isDoctor",false);
        return is;

    }

}
