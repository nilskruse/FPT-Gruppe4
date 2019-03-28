package net;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import interfaces.ClientRemote;
import interfaces.Playlist;
import interfaces.Song;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import view.View;

public class RMIClient extends UnicastRemoteObject implements ClientRemote {
	private static final long serialVersionUID = 1L;
	private transient View view;

	public RMIClient(View view) throws RemoteException {
		super();
		this.view = view;
	}

	@Override
	public void updateView(Playlist library, Playlist playlist, int lib, int pl) throws RemoteException {

		Platform.runLater(() -> {
			view.getList().setItems((ObservableList<Song>) library);
			view.getPlaylist().setItems((ObservableList<Song>) playlist);

			view.getList().getSelectionModel().selectIndices(lib);
			view.getPlaylist().getSelectionModel().selectIndices(pl);

			view.getList().refresh();
			view.getPlaylist().refresh();
		});

	}
}
