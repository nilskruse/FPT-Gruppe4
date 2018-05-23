package controller;

import javafx.beans.value.ChangeListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import model.Model;
import interfaces.Song;
import org.apache.commons.lang.ObjectUtils;
import view.ShowError;
import view.View;

import java.io.File;
import java.io.IOException;


public class Controller {

    private Model model;
    private View view;
    private int songPointer;

    public void link(Model model, View view) {
        this.model = model;
        this.view = view;
        view.getList().setItems(model.getLibrary());
        view.getPlaylist().setItems(model.getPlaylist());
        view.addController(this);
        addSongsFromFolder(model);
    }

    private void add(Song s) {
        model.getLibrary().addSong(s);
    }

    public void addToPlaylist() {
        Song selectedSongInLibrary = view.getList().getSelectionModel().getSelectedItem();
        if (selectedSongInLibrary != null) {
            model.getPlaylist().addSong(selectedSongInLibrary);
        } else {
            selectionEmptyError();
        }
    }

    public void addAllToPlaylist() {
        for (Song s : model.getLibrary()) {
            model.getPlaylist().addSong(s);
        }
    }

    public void changeSongProperties(Song s, String title, String album, String interpret) {
        try {
            if ( s != null )
            {
                s.setTitle(title);
                s.setAlbum(album);
                s.setInterpret(interpret);
            }
            else
            {
                selectionEmptyError();
            }
        } catch(NullPointerException e) {
            selectionEmptyError();
        }
    }

    public void deleteSongFromPlaylist() {
        try {
            if (songPointer == view.getPlaylist().getSelectionModel().getSelectedIndex() && model.getPlayer() != null) {
                model.getPlayer().dispose();
                view.getPlayButton().setSelected(false);
            }
            model.getPlaylist().remove(view.getPlaylist().getSelectionModel().getSelectedIndex());
            view.getPlayTime().setText("0:00 / 0:00");
        } catch (NullPointerException | IndexOutOfBoundsException e){
            selectionEmptyError();
        }
    }


    public void play() {

        //If no different song is selected resume playback of paused song
        if(model.getPlayer() != null && songPointer == view.getPlaylist().getSelectionModel().getSelectedIndex() && model.getPlayer().getStatus() == MediaPlayer.Status.PAUSED){
            model.getPlayer().play();
            toggleButton(true);
            return;
        }

        //dispose of old MediaPlayer object
        if (model.getPlayer() != null) {
            model.getPlayer().dispose();
        }

        try {
            if(!view.getPlaylist().getSelectionModel().isEmpty()){
                songPointer = view.getPlaylist().getSelectionModel().getSelectedIndex();
            } else {
                //if nothing is selected play first song
                songPointer = 0;
                view.getPlaylist().getSelectionModel().select(songPointer);
            }
            model.setPlayer(new MediaPlayer(new Media(new File(model.getPlaylist().get(songPointer).getPath()).toURI().toString())));
            model.getPlayer().play();
            toggleButton(true);


            // da die MediaPlayer überschrieben werden, muss das Event immer wieder neu gesetzt werden
            model.getPlayer().setOnEndOfMedia(this::endOfMediaEvent);

            //Zeit anzeigen
            model.getPlayer().currentTimeProperty().addListener((ChangeListener) (o, oldVal, newVal) -> {

                Duration d = (Duration) newVal;
                Duration tD = model.getPlayer().getTotalDuration();
                String r,s;
                if(((int)d.toSeconds() % 60) < 10){
                    r = (int)d.toMinutes() + ":0" + (int)(d.toSeconds() % 60);
                }else{
                    r = (int)d.toMinutes() + ":" + (int)(d.toSeconds() % 60);
                }

                if(((int) tD.toSeconds() % 60) < 10){
                    s = (int)tD.toMinutes() + ":0" + (int)(tD.toSeconds() % 60);
                }else{
                    s = (int)tD.toMinutes() + ":" + (int)(tD.toSeconds() % 60);
                }

                view.getPlayTime().setText(r + " / " + s);

            });
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            playlistEmptyError();
            view.getPlayButton().setSelected(false);

        }
    }


    public void pause() {
        try {
            if (model.getPlaylist().isEmpty()) {
                model.getPlayer().pause();
                toggleButton(false);
            }
            MediaPlayer.Status status = model.getPlayer().getStatus();

            if (!model.getPlaylist().isEmpty() && (status == MediaPlayer.Status.PAUSED || status == MediaPlayer.Status.DISPOSED || status == MediaPlayer.Status.STOPPED)) {
                playlistNotPlayError();
                view.getPauseButton().setSelected(false);
            }
        } catch (NullPointerException e){
            playlistNotPlayError();
            view.getPauseButton().setSelected(false);
        }
    }

    public void next() {

        if(model.getPlaylist().isEmpty()){
            playlistEmptyError();
            return;
        }
        try {

            if (songPointer + 1 < model.getPlaylist().size()) {
                // id des Songs neu setzten
                view.getPlaylist().getSelectionModel().select(songPointer + 1);
                play();
            } else {
                // Song null abspielen Playlist von vorne starten
                view.getPlaylist().getSelectionModel().select(0);
                play();
            }

        } catch (NullPointerException e) {
            // falls kein Song ausgewählt ist wird hier einfach Play aufgerufen sodass der erste songe gespielt wird
            view.getPlaylist().getSelectionModel().select(0);
            this.play();
        }


    }

    private void endOfMediaEvent() {
        next();
    }

    private void addSongsFromFolder(Model model) {
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

    private void playlistEmptyError() {
        ShowError.infoBox("Bitte füge Lieder zur Playlist hinzu.", "Fehler beim abspielen");
    }
    private void playlistNotPlayError() {
        ShowError.infoBox("Bitte starte erst ein Lied bevor du es pausierst.", "Fehler beim pausieren");
    }

    private void selectionEmptyError (){
        ShowError.infoBox("Es ist kein Lied ausgewählt", "Fehler");
    }

    //toggleButton method
    private void toggleButton(boolean isPlaying){
        view.getPlayButton().setSelected(isPlaying);
        view.getPauseButton().setSelected(!isPlaying);
    }

}