package controller;

import interfaces.Playlist;
import interfaces.SerializableStrategy;
import interfaces.Song;
import model.Model;

import java.io.*;

public class BinaryStrategy implements SerializableStrategy {
    private String name = "Binary";

    FileOutputStream libraryFO;
    ObjectOutputStream libraryOS;

    FileInputStream libraryFI;
    ObjectInputStream libraryIS;

    FileOutputStream playlistFO;
    ObjectOutputStream playlistOS;

    FileInputStream playlistFI;
    ObjectInputStream playlistIS;

    @Override
    public void openWritableLibrary() throws IOException {
        libraryFO = new FileOutputStream("library.ser");
        libraryOS = new ObjectOutputStream(libraryFO);
    }

    @Override
    public void openReadableLibrary() throws IOException {
        libraryFI = new FileInputStream("library.ser");
        libraryIS = new ObjectInputStream(libraryFI);
    }

    @Override
    public void openWritablePlaylist() throws IOException {
        playlistFO = new FileOutputStream("playlist.ser");
        playlistOS = new ObjectOutputStream(playlistFO);
    }

    @Override
    public void openReadablePlaylist() throws IOException {
        playlistFI = new FileInputStream("playlist.ser");
        playlistIS = new ObjectInputStream(playlistFI);
    }

    @Override
    public void writeSong(Song s) throws IOException {

    }

    @Override
    public Song readSong() throws IOException, ClassNotFoundException {
        return null;
    }

    @Override
    public void writeLibrary(Playlist p) throws IOException {
        libraryOS.writeObject(p);
    }

    @Override
    public Playlist readLibrary() throws IOException, ClassNotFoundException {
        return (Playlist) libraryIS.readObject();
    }

    @Override
    public void writePlaylist(Playlist p) throws IOException {
        playlistOS.writeObject(p);
    }

    @Override
    public Playlist readPlaylist() throws IOException, ClassNotFoundException {
        return (Playlist) playlistIS.readObject();
    }



    @Override
    public void closeWritableLibrary() {

            try {
                if(libraryOS != null){
                    libraryOS.flush();
                    libraryOS.close();
                }
                if(libraryFO != null){
                    libraryFO.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    @Override
    public void closeReadableLibrary() {

            try {
                if(libraryIS != null){
                    libraryIS.close();
                }

                if(libraryFI != null){
                    libraryFI.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    @Override
    public void closeWritablePlaylist() {

        try {
            if(playlistOS != null){
                playlistOS.flush();
                playlistOS.close();
            }
            if(playlistFO != null){
                playlistFO.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void closeReadablePlaylist() {

        try {
            if(playlistIS != null){
                playlistIS.close();
            }

            if(playlistFI != null){
                playlistFI.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void load(Model model) {
        interfaces.Playlist rL,rP;
        try {
            openReadableLibrary();
            rL = readLibrary();
            IDGenerator.reset();
            model.getLibrary().clearPlaylist();
            for(Song s : rL){
                s.setId(IDGenerator.getNextID());
                model.getLibrary().add(s);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IDOverFlowException e) {
            e.printStackTrace();
        } finally {
            closeReadableLibrary();
        }

        try {
            openReadablePlaylist();
            rP = readPlaylist();
            model.getPlaylist().clearPlaylist();
            for(Song s : rP){
                model.getPlaylist().add(model.getLibrary().findSongByID(s.getId()));
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeReadablePlaylist();
        }
    }

    @Override
    public void save(Model model) {
        try {
            openWritableLibrary();
            writeLibrary(model.getLibrary());

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            closeWritableLibrary();
        }

        try {
            openWritablePlaylist();
            writePlaylist(model.getPlaylist());

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            closeWritablePlaylist();
        }
    }

}
