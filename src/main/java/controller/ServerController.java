package controller;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import interfaces.ClientRemote;
import interfaces.SerializableStrategy;

public class ServerController extends Controller {
	private static final Logger LOG = Logger.getGlobal();
	private List<String> clientlist;

	public ServerController(List<String> clientlist) {
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
	public synchronized void addToPlaylist(int index) {
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

	@Override
	public void selectStrategy(String strat) {

		for (SerializableStrategy s : strats) {
			if (s.toString().equals(strat)) {
				view.getDropdown().getSelectionModel().select(view.getDropdown().getItems().indexOf(s));
				ser.setStrat(s);
			}
		}
		LOG.log(Level.INFO, "Select");

	}

	public void updateAllClients() {
		for (String clientname : clientlist) {
			ClientRemote client = null;
			try {
				client = (ClientRemote) Naming.lookup("//localhost/" + clientname);
				client.updateView(getLibrary(), getPlaylist(), getLibraryView().getSelectionModel().getSelectedIndex(),
						getPlaylistView().getSelectionModel().getSelectedIndex());
			} catch (NotBoundException | MalformedURLException | RemoteException e) {
				LOG.log(Level.SEVERE, EXCEPTION_STRING, e);
			}
		}

	}
}
