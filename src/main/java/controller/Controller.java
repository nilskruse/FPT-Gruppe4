package controller;

import model.Model;
import interfaces.Song;
import view.View;

public class Controller{

    private Model model;

    public void link(Model model, View view){
        this.model = model;
        view.getList().setItems(model.getLibrary());
        view.addController(this);
    }
    public void add(Song s){

        model.getLibrary().addSong(s);
    }

}