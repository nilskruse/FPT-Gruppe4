package controller;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import model.Model;
import interfaces.Song;
import view.ShowError;
import view.View;

import java.io.File;
import java.io.IOException;


public class Controller{

    private Model model;

    public void link(Model model, View view){
        this.model = model;
        view.getList().setItems(model.getLibrary());
        view.getPlaylist().setItems(model.getPlaylist());
        view.addController(this);
        addSongsFromFolder(model);
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
    public void changeSongProperties(Song s, String title, String album, String interpret ) {
        s.setTitle(title);
        s.setAlbum(album);
        s.setInterpret(interpret);


    }

    public void addSongsFromFolder(Model model){
        long id = model.getLibrary().size() + 1;
        // initialize File object
        File file = new File("songs");

        // check if the specified pathname is directory first
        if(file.isDirectory()){
            //list all files on directory
            File[] files = file.listFiles();
            for(File f:files){
                try {
                    model.getLibrary().addSong(new model.Song(f.getName(),"","",f.getCanonicalPath(),id));
                    id++;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void play(Song s)
    {
            if(model.getPlayer() != null)
            {
                model.getPlayer().dispose();
            }

            try {
                model.setPlayer(new MediaPlayer(new Media(new File( s.getPath()).toURI().toString())));
                model.getPlayer().play();
            }
            catch (NullPointerException e)
            {

               try
               {
                   model.setPlayer(new MediaPlayer(new Media(new File( model.getPlaylist().findSongByID(1).getPath()).toURI().toString())));
                   View.getPlaylist().getSelectionModel().select(0);
                   model.getPlayer().play();
               }
               catch(NullPointerException f)
                {
                    this.PlaylistEmptyError();
                }

            }

    }

    public void pause()
    {
        try
        {
            model.getPlayer().pause();
        }catch (NullPointerException e)
        {
            PlaylistNotPlayError();
        }

    }
    public  void next(Song s)
    {

            long id;
            try {

                id = s.getId()+1;
                if(id <= model.getPlaylist().size() )
                {
                    // id des Songs neu setzten
                    View.getPlaylist().getSelectionModel().select((int) id - 1);
                    play(View.getPlaylist().getSelectionModel().getSelectedItem());
                }
                else
                {
                    // Song null abspielen Playlist von vorne starten
                    View.getPlaylist().getSelectionModel().select(0);
                    play(View.getPlaylist().getSelectionModel().getSelectedItem());
                }

            }
            catch (NullPointerException e)
            {
                // falls kein Song ausgewählt ist wird hier einfach Play aufgerufen sodass der erste songe gespielt wird
                this.play(s);
            }




    }
     // Eventuelle Fehler Kontrollklasse  ?

  public void PlaylistEmptyError()
   {
       ShowError.infoBox("Bitte füge Lieder zur Playlist hinzu.", "Fehler beim abspielen");
   }


    public void PlaylistNotPlayError() {
        ShowError.infoBox("Bitte starte erst ein Lied bevor du es pausierst.", "Fehler beim pausieren");
    }
}