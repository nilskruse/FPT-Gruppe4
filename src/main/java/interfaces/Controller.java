package interfaces;

import javafx.scene.control.ListView;
import model.Model;
import view.View;

import java.rmi.Remote;
import java.rmi.RemoteException;

    public interface Controller {
        void link(Model model, View view);
        void add(Song s);
        void addToPlaylist (int index);
        void addAllToPlaylist();
        void changeSongProperties(int libindex, String title, String album, String interpret);
        void deleteSongFromPlaylist(int index);
        void play ();
        void pause ();
        void next ();
        void addSongsFromFolder(String folder);
        void selectStrategy();
        void load();
        void save();
        Playlist getLibrary();
        Playlist getPlaylist();
        ListView<Song> getLibraryView();
        ListView<Song> getPlaylistView();
    }


