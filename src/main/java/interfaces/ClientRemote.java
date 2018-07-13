package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientRemote extends Remote {
    void updateView(Playlist library, Playlist playlist,int lib,int pl) throws RemoteException;
}
