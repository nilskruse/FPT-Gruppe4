package controller;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import model.Model;
import interfaces.Song;
import view.ShowError;
import view.View;

import java.io.File;
import java.io.IOException;


public class Controller {

    private Model model;
    private View view;
    private int songPointer;
    private boolean firstMediaPlayerInitilization = true;

    public void link(Model model, View view) {
        this.model = model;
        this.view = view;
        view.getList().setItems(model.getLibrary());
        view.getPlaylist().setItems(model.getPlaylist());
        view.addController(this);
        addSongsFromFolder(model);


    }

    public void add(Song s) {

        model.getLibrary().addSong(s);
    }

    public void addToPlaylist(Song s) {
        model.getPlaylist().addSong(s);
        //model.getLibrary().deleteSong(s);
    }

    public void addAllToPlaylist() {
        for (Song s : model.getLibrary()) {
            model.getPlaylist().addSong(s);
        }
    }

    public void deleteSongFromPlaylist(int index) {

        if(songPointer == index && model.getPlayer() != null){
            model.getPlayer().dispose();
        }
        model.getPlaylist().remove(index);

    }

    public void changeSongProperties(Song s, String title, String album, String interpret) {
        s.setTitle(title);
        s.setAlbum(album);
        s.setInterpret(interpret);
    }



    public void play(int index) {

        if (model.getPlayer() != null) {
            model.getPlayer().dispose();
        }

        try {
            model.setPlayer(new MediaPlayer(new Media(new File(model.getPlaylist().get(index).getPath()).toURI().toString())));
            model.getPlayer().play();
            songPointer = index;
        } catch (NullPointerException e) {

            try {
                model.setPlayer(new MediaPlayer(new Media(new File(model.getPlaylist().findSongByID(1).getPath()).toURI().toString())));
                view.getPlaylist().getSelectionModel().select(0);
                model.getPlayer().play();
            } catch (NullPointerException f) {
                this.PlaylistEmptyError();
            }

        }

        // da die MediaPlayer überschrieben werden, muss das Event immer wieder neu gesetzt werden
        model.getPlayer().setOnEndOfMedia(this::endOfMediaEvent);

    }



    public void pause() {

        if(model.getPlayer() != null){
            model.getPlayer().pause();
        }
        MediaPlayer.Status status = model.getPlayer().getStatus();

        if(status == MediaPlayer.Status.PAUSED || status == MediaPlayer.Status.DISPOSED || status == MediaPlayer.Status.STOPPED){
            PlaylistNotPlayError();
        }


    }

    public void next() {

        if(model.getPlaylist().isEmpty()){
            PlaylistEmptyError();
            return;
        }
        try {

            if (songPointer + 1 < model.getPlaylist().size()) {
                // id des Songs neu setzten
                view.getPlaylist().getSelectionModel().select(songPointer + 1);
                play(songPointer + 1);
            } else {
                // Song null abspielen Playlist von vorne starten
                view.getPlaylist().getSelectionModel().select(0);
                play(0);
            }

        } catch (NullPointerException e) {
            // falls kein Song ausgewählt ist wird hier einfach Play aufgerufen sodass der erste songe gespielt wird
            this.play(0);
        }


    }

    private void endOfMediaEvent() {
        next();
    }

    public void addSongsFromFolder(Model model) {
        long id = model.getLibrary().size() + 1;
        // initialize File object
        File file = new File("songs");

        // check if the specified pathname is directory first
        if (file.isDirectory()) {
            //list all files on directory
            File[] files = file.listFiles();
            for (File f : files) {
                try {
                    add(new model.Song(f.getName(), "", "", f.getCanonicalPath(), id));
                    id++;
                } catch (IOException | NullPointerException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public boolean isPlaying(){
        return model.getPlayer().getStatus() == MediaPlayer.Status.PLAYING;
    }
    // Eventuelle Fehler Kontrollklasse  ?

    public void PlaylistEmptyError() {
        ShowError.infoBox("Bitte füge Lieder zur Playlist hinzu.", "Fehler beim abspielen");
    }
    public void PlaylistNotPlayError() {
        ShowError.infoBox("Bitte starte erst ein Lied bevor du es pausierst.", "Fehler beim pausieren");
    }


}