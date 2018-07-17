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
    public synchronized void play() {
        super.play();
        updateAllClients();
    }

    @Override
    public synchronized void next() {
        super.next();
        updateAllClients();
    }

    @Override
    public synchronized void pause() {
        super.pause();
        updateAllClients();
    }


    @Override
    public synchronized void addToPlaylist(int index){
        super.addToPlaylist(index);
        updateAllClients();
    }

    @Override
    public synchronized void deleteSongFromPlaylist(int index) {
        super.deleteSongFromPlaylist(index);
        updateAllClients();
    }

    @Override
    public synchronized void changeSongProperties(int libindex, String title, String album, String interpret) {
        super.changeSongProperties(libindex, title, album, interpret);
        getPlaylistView().refresh();
        getLibraryView().refresh();
        updateAllClients();
    }

    @Override
    public synchronized void addAllToPlaylist() {
        super.addAllToPlaylist();
        updateAllClients();
    }

    @Override
    public synchronized void load() {
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
