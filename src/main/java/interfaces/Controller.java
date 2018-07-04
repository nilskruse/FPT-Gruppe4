package interfaces;

import model.Model;
import view.View;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Controller extends Remote {
    void link(Model model, View view) throws RemoteException;
    void add(Song s) throws RemoteException;
    void addToPlaylist () throws RemoteException;
    void addAllToPlaylist() throws RemoteException;
    void changeSongProperties(Song s, String title, String album, String interpret) throws RemoteException;
    void deleteSongFromPlaylist() throws RemoteException;
    void play () throws RemoteException;
    void pause () throws RemoteException;
    void next () throws RemoteException;
    //void addSongsFromFolder(String folder) throws RemoteException;
    void selectStrategy() throws RemoteException;
    void load() throws RemoteException;
    void save() throws RemoteException;
}

