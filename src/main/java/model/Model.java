package model;

public class Model{
    private Playlist all = new Playlist();
    private Playlist playlist = new Playlist();

    public Playlist getAllPlaylist() {
        return all;
    }

    public Playlist getPlaylist() {
        return playlist;
    }
}