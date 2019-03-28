package controller;

import java.rmi.RemoteException;

import interfaces.SerializableStrategy;
import interfaces.ServerRemote;

public class ClientController extends Controller {
	private ServerRemote server;

	public ClientController(ServerRemote server) {
		super();
		this.server = server;
	}

	@Override
	public void selectStrategy() {
		try {
			server.selectStrategy(
					((SerializableStrategy) view.getDropdown().getSelectionModel().getSelectedItem()).toString());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void play() {
		try {
			server.play();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void next() {
		try {
			server.next();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void pause() {
		try {
			server.pause();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addToPlaylist(int index) {
		try {
			server.addSongToPlaylist(index);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteSongFromPlaylist(int index) {
		try {
			server.deleteSongFromPlaylist(index);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addAllToPlaylist() {
		try {
			server.addAllToPlaylist();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void changeSongProperties(int libindex, String title, String album, String interpret) {
		try {
			server.changeSongProperties(libindex, title, album, interpret);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void load() {
		try {
			server.load();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void save() {
		try {
			server.save();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
