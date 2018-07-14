package controller;

import id.IDGenerator;
import id.IDOverFlowException;
import interfaces.Playlist;
import interfaces.SerializableStrategy;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import model.Model;
import interfaces.Song;
import serialization.*;
import view.ShowError;
import view.View;

import java.io.File;
import java.io.IOException;


public class Controller implements interfaces.Controller{

    private Model model;
    private View view;
    private int songPointer;
    private String playTime;
    private Serialization ser = new Serialization();
    private ObservableList<SerializableStrategy> strats = FXCollections.observableArrayList();


    public void link(Model model, View view) {
        this.model = model;
        this.view = view;
        view.getList().setItems(model.getLibrary());
        view.getPlaylist().setItems(model.getPlaylist());
        view.getDropdown().setItems(strats);
        view.addController(this);
        addSongsFromFolder("src/main/resources/songs");

        strats.add(new BinaryStrategy());
        strats.add(new XMLStrategy());
        strats.add(new JDBCStrategy());
        strats.add(new OpenJPAStrategy());


    }

    public void add(Song s) {

        try {
            s.setId(IDGenerator.getNextID());
            model.getLibrary().addSong(s);
        } catch (IDOverFlowException e) {
            e.printStackTrace();
        }
    }

    public void addToPlaylist(int index) {
        if (index != -1) {
            model.getPlaylist().addSong(model.getLibrary().get(index));
        } else {
            selectionEmptyError();
        }
    }

    public void addAllToPlaylist() {
        if (!model.getLibrary().isEmpty()) {
            for (Song s : model.getLibrary())
            {
                model.getPlaylist().addSong(s);
            }
        } else {
            nothingInPlaylist();
        }


    }

    public void changeSongProperties(int libindex, String title, String album, String interpret) {
        Song s = model.getLibrary().get(libindex - 1);
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

    public void deleteSongFromPlaylist(int index) {
        try {
            if (songPointer == index && model.getPlayer() != null) {
                model.getPlayer().dispose();
                view.getPlayButton().setSelected(false);
            }
            model.getPlaylist().remove(index);
            view.getPlayTime().setText("00:00 / 00:00");
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
           // System.out.println();
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
                String rNullsek ="";
                String sNullsek = "";
                String rNullmin ="";
                String sNullmin = "";

                if(((int)d.toSeconds() % 60) < 10){
                    rNullsek = "0";
                       // r = (int)d.toMinutes() + ":0" + (int)(d.toSeconds() % 60);
                }
                if(((int)d.toMinutes() % 60) < 10){
                    rNullmin = "0";
                    // r = (int)d.toMinutes() + ":0" + (int)(d.toSeconds() % 60);
                }
                if(((int) tD.toSeconds() % 60) < 10){
                    sNullsek = "0";
                    // s = (int)tD.toMinutes() + ":0" + (int)(tD.toSeconds() % 60);
                }
                if(((int) tD.toMinutes() % 60) < 10){
                    sNullmin = "0";
                    // s = (int)tD.toMinutes() + ":0" + (int)(tD.toSeconds() % 60);
                }

                    s = sNullmin +(int)tD.toMinutes() + ":"+ sNullsek + (int)(tD.toSeconds() % 60);
                    r = rNullmin +(int)d.toMinutes() + ":" +rNullsek + (int)(d.toSeconds() % 60);
                    view.getPlayTime().setText(r + " / " + s);
                    this.playTime = r + " / " + s ;
            });
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            playlistEmptyError();
            view.getPlayButton().setSelected(false);

        }
    }

    public String getPlayTime()
    {
        return this.playTime;
    }
    public void setPlayTime(String time)
    {
        view.getPlayTime().setText(time);
    }
    public void pause() {
        try {
            if (!model.getPlaylist().isEmpty()) {
                model.getPlayer().pause();
                toggleButton(false);
            }
            MediaPlayer.Status status = model.getPlayer().getStatus();

            if (model.getPlaylist().isEmpty() || (status == MediaPlayer.Status.PAUSED || status == MediaPlayer.Status.DISPOSED || status == MediaPlayer.Status.STOPPED)) {
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

    public void addSongsFromFolder(String folder) {
        // initialize File object
        File file = new File(folder);
        // check if the specified pathname is directory first
        if (file.isDirectory()) {
            //list all files on directory
            File[] files = file.listFiles();
            for (File f : files) {
                try {
                    if(f.getName().endsWith(".mp3") || f.getName().endsWith(".m4a")) {
                        add(new model.Song(f.getName(), "", "", f.getCanonicalPath(), 0));
                    }
                } catch (IOException | NullPointerException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void SelectedStrategyError(){
        ShowError.infoBox("Bitte wähle eine andere Serialisierungstrategie.", "Serialisierungsfehler");
    }

    private void playlistEmptyError() {
        ShowError.infoBox("Bitte füge Lieder zur Playlist hinzu.", "Fehler beim abspielen");
    }
    private void playlistNotPlayError() {
        ShowError.infoBox("Bitte starte erst ein Lied bevor du es pausierst.", "Fehler beim pausieren");
    }

    private void selectionEmptyError (){
        ShowError.infoBox("Es ist kein Lied ausgewählt", "Auswahl Fehler");
    }
    private void nothingInPlaylist(){
        ShowError.infoBox("Es sind keine Lieder in der Library", "Library Fehler");
    }

    //toggleButton method
    private void toggleButton(boolean isPlaying){
        view.getPlayButton().setSelected(isPlaying);
        view.getPauseButton().setSelected(!isPlaying);
    }

    public void selectStrategy(){

        ser.setStrat((SerializableStrategy) view.getDropdown().getSelectionModel().getSelectedItem());
        System.out.println("select");

    }

    public void load(){
        try {
            ser.load(model);
            System.out.println("load");
        }catch (Exception e){
            e.printStackTrace();
            SelectedStrategyError();
        }

    }

    public void save(){
        try {
            ser.save(model);
            System.out.println("save");
        }catch (NullPointerException e){
            SelectedStrategyError();
        }

    }

    public ListView<Song> getLibraryView(){
        return view.getList();
    }
    public ListView<Song> getPlaylistView(){

        return view.getPlaylist();
    }
    public Playlist getLibrary(){
        return model.getLibrary();
    }
    public Playlist getPlaylist(){

        return model.getPlaylist();
    }

}