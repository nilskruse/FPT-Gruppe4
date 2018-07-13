package controller;

import interfaces.ClientRemote;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ServerController extends Controller {
    private ArrayList<String> clientlist;
    public ServerController(ArrayList<String> clientlist){
        super();
        this.clientlist = clientlist;
    }

    @Override
    public void play() {
        super.play();
        updateAllClients();
    }

    @Override
    public void next() {
        super.next();
        updateAllClients();
    }

    @Override
    public void pause() {
        super.pause();
        updateAllClients();
    }


    @Override
    public void addToPlaylist(int index){
        super.addToPlaylist(index);
        updateAllClients();
    }

    @Override
    public void deleteSongFromPlaylist(int index) {
        super.deleteSongFromPlaylist(index);
        updateAllClients();
    }

    @Override
    public void changeSongProperties(int libindex, String title, String album, String interpret) {
        super.changeSongProperties(libindex, title, album, interpret);
        getPlaylistView().refresh();
        getLibraryView().refresh();
        updateAllClients();
    }

    @Override
    public void addAllToPlaylist() {
        super.addAllToPlaylist();
        updateAllClients();
    }

    @Override
    public void load() {
        super.load();
        updateAllClients();
    }

    public void updateAllClients() {
        for(String clientname : clientlist){
            ClientRemote client = null;
            try {
                client = (ClientRemote) Naming.lookup("//localhost/" + clientname);
                client.updateView(getLibrary(),getPlaylist(),getLibraryView().getSelectionModel().getSelectedIndex(),getPlaylistView().getSelectionModel().getSelectedIndex());
            } catch (NotBoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

    }
}
