package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerRemote extends Remote {
	void play() throws RemoteException;

	void next() throws RemoteException;

	void pause() throws RemoteException;

	void addSongToPlaylist(int index) throws RemoteException;

	void deleteSongFromPlaylist(int index) throws RemoteException;

	void addAllToPlaylist() throws RemoteException;

	void changeSongProperties(int libindex, String title, String album, String interpret) throws RemoteException;

	void load() throws RemoteException;

	void save() throws RemoteException;

	void update() throws RemoteException;

	void selectStrategy(String strat) throws RemoteException;
}
