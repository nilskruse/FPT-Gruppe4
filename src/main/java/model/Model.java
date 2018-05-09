package model;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Model{
    private Playlist library = new Playlist();
    private Playlist playlist = new Playlist();
    private MediaPlayer player ;

    public Playlist getLibrary() {

        return library;
    }

    public Playlist getPlaylist() {

        return playlist;
    }
    public MediaPlayer getplayer() {

        return player;
    }
    public void  setplayer(MediaPlayer player) {

        this.player = player;
    }

}