package com.example.knee_must;

public class Presenter implements IPrestenter{
    private Model model;
    private IView view;

    public Presenter(IView view) {
        this.view = view;
        this.model=new Model();
    }
    public void ViewtoModel(double angle) {
        if(!model.GoodExercise(angle))
        {

        }
    }
    public void MessagetoView(){
        view.Displaymessage("change exercise");
    }
}
