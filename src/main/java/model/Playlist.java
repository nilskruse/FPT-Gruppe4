package model;

import interfaces.Song;
import javafx.collections.ModifiableObservableListBase;

import java.util.ArrayList;

public class Playlist extends ModifiableObservableListBase<Song> implements interfaces.Playlist{

    public boolean addSong(Song s) {

        return this.add(s);
    }

    public boolean deleteSong(Song s) {

        return this.remove(s);
    }

    public boolean deleteSongByID(long id) {
        for (Song s : this) {
            if(s.getId() == id){
                return this.remove(s);
            }
        }
        return false;
    }

    public void setList(ArrayList<Song> list) {
        this.clear();
        for(Song s : list){
            this.addSong(s);
        }
    }

    public ArrayList<Song> getList() {
        ArrayList<Song> r = new ArrayList<Song>();
        for(Song s : this){
            r.add(s);
        }
        return r;
    }

    public void clearPlaylist() {

        this.clear();
    }

    public int sizeOfPlaylist() {

        return this.size();
    }

    public Song findSongByPath(String name) {
        for (Song s : this) {
            if(s.getPath() == name){
                return s;
            }
        }
        return null;
    }

    public Song findSongByID(long id) {
        for (Song s : this) {
            if(s.getId() == id){
                return s;
            }
        }
        return null;
    }

    @Override
    public Song get(int index) {

        return this.get(index);
    }

    @Override
    public int size() {

        return this.size();
    }


    @Override
    protected void doAdd(int index, Song element) {

        this.add(index,element);
    }

    @Override
    protected Song doSet(int index, Song element) {

        return this.set(index,element);
    }

    @Override
    protected Song doRemove(int index) {

        return this.remove(index);
    }
}