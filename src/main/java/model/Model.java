package model;

public class Model{
    private Playlist library = new Playlist();
    private Playlist playlist = new Playlist();

    public Playlist getLibrary() {

        return library;
    }

    public Playlist getPlaylist() {

        return playlist;
    }
}