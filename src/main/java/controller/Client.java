package controller;

import interfaces.ClientRemote;
import interfaces.Playlist;
import interfaces.Song;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import model.Model;
import view.View;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Client extends UnicastRemoteObject implements ClientRemote {
    private View view;
    public Client(View view) throws RemoteException {
        super();
        this.view = view;
    }

    @Override
    public void updateView(Playlist library, Playlist playlist,int lib, int pl) throws RemoteException,IllegalStateException {

        Platform.runLater(() ->{
            view.getList().setItems((ObservableList<Song>) library);
            view.getPlaylist().setItems((ObservableList<Song>) playlist);

            view.getList().getSelectionModel().selectIndices(lib);
            view.getPlaylist().getSelectionModel().selectIndices(pl);

            view.getList().refresh();
            view.getPlaylist().refresh();
        });

    }
}
