package com.example.knee_must;

public class ExerciseLevel extends Exercise{

    private String level;
    private int x;

    public ExerciseLevel(String level,int x )
    {
        this.level=level;
        this.x=x;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }
}
