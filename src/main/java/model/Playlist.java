package model;

import interfaces.Song;
import javafx.collections.ModifiableObservableListBase;

import java.util.ArrayList;

public class Playlist extends ModifiableObservableListBase<Song> implements interfaces.Playlist{
    private ArrayList<Song> playlist = new ArrayList<Song>();

    @Override
    public boolean addSong(Song s) {
        return playlist.add(s);
    }

    @Override
    public boolean deleteSong(Song s) {
        return playlist.remove(s);
    }

    @Override
    public boolean deleteSongByID(long id) {
        return playlist.removeIf(song -> song.getId() == id);
    }

    public void setList(ArrayList<Song> playlist) {
        this.playlist = playlist;
    }

    public ArrayList<Song> getList() {
        return playlist;
    }

    @Override
    public void clearPlaylist() {
        playlist.clear();
    }

    @Override
    public int sizeOfPlaylist() {
        return playlist.size();
    }

    @Override
    public Song findSongByID(long id) {
        for(Song s : playlist){
            if(s.getId() == id){
                return s;
            }
        }
        return null;
    }

    @Override
    public Song findSongByPath(String path) {
        for(Song s : playlist){
            if(s.getPath() == path){
                return s;
            }
        }
        return null;
    }

    @Override
    public Song get(int index) {
        return playlist.get(index);
    }

    @Override
    public int size(){
        return sizeOfPlaylist();
    }

    @Override
    protected void doAdd(int index, Song s){
        playlist.add(index,s);
    }

    @Override
    protected Song doSet(int index, Song s){
        return playlist.set(index,s);
    }

    @Override
    protected Song doRemove(int index){
        return playlist.remove(index);
    }

}