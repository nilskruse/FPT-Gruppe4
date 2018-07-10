package controller;


import interfaces.Song;
import model.Model;
import view.View;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerContr extends UnicastRemoteObject implements interfaces.Controller {


    protected ServerContr() throws RemoteException {
    }

    @Override
    public void link(Model model, View view) throws RemoteException {

    }

    @Override
    public void add(Song s) throws RemoteException {

    }

    @Override
    public void addToPlaylist() throws RemoteException {

    }

    @Override
    public void addAllToPlaylist() throws RemoteException {

    }

    @Override
    public void changeSongProperties(Song s, String title, String album, String interpret) throws RemoteException {

    }

    @Override
    public void deleteSongFromPlaylist() throws RemoteException {

    }

    @Override
    public void play() throws RemoteException {

    }

    @Override
    public void pause() throws RemoteException {

    }

    @Override
    public void next() throws RemoteException {

    }

    @Override
    public void selectStrategy() throws RemoteException {

    }

    @Override
    public void load() throws RemoteException {

    }

    @Override
    public void save() throws RemoteException {

    }
}
