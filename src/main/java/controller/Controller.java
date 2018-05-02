package controller;

import model.Model;
import interfaces.Song;
import view.View;

public class Controller{

    private Model model;

    public void link(Model model, View view){
        this.model = model;
        view.getList().setItems(model.getLibrary());
        view.getPlaylist().setItems(model.getPlaylist());
        view.addController(this);
    }
    public void add(Song s){

        model.getLibrary().addSong(s);
    }
    public void addToPlaylist(Song s){
        model.getPlaylist().addSong(s);
        //model.getLibrary().deleteSong(s);
    }
    public void addAllToPlaylist(){
        for(Song s : model.getLibrary()){
            model.getPlaylist().addSong(s);
        }
    }
    public void deleteSongFromPlaylist(Song s){
        model.getPlaylist().deleteSong(s);
    }
    public void changeSongProperties(Song s, String title, String album, String interpret ){
        s.setTitle(title);
        s.setAlbum(album);
        s.setInterpret(interpret);



    }
}