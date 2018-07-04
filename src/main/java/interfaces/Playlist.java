package interfaces;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;


public interface Playlist extends Serializable, Iterable<Song>{
    boolean addSong(Song s);
    boolean deleteSong(Song s);
    boolean deleteSongByID(long id);
    void setList(ArrayList<Song> s);
    ArrayList<Song> getList();
    void clearPlaylist();
    int sizeOfPlaylist();
    Song findSongByPath(String name);
    Song findSongByID(long id);



}
