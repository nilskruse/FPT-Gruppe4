package interfaces;

import javafx.scene.control.ListView;
import model.Model;
import view.View;

import java.rmi.Remote;
import java.rmi.RemoteException;

    //Client braucht noch andere Methoden? müssen alle remoteexception schmeißen?
    public interface Controller extends Remote {
        void link(Model model, View view) throws RemoteException;
        void add(Song s) throws RemoteException;
        void addToPlaylist (int index) throws RemoteException;
        void addAllToPlaylist() throws RemoteException;
        void changeSongProperties(int libindex, String title, String album, String interpret) throws RemoteException;
        void deleteSongFromPlaylist(int index) throws RemoteException;
        void play () throws RemoteException;
        void pause () throws RemoteException;
        void next () throws RemoteException;
        void addSongsFromFolder(String folder) throws RemoteException;
        void selectStrategy() throws RemoteException;
        void load() throws RemoteException;
        void save() throws RemoteException;
        Playlist getLibrary();
        Playlist getPlaylist();
        ListView<Song> getLibraryView();
        ListView<Song> getPlaylistView();
    }


