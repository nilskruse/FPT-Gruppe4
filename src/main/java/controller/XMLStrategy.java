package controller;

import interfaces.Playlist;
import interfaces.SerializableStrategy;
import interfaces.Song;
import model.Model;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

public class XMLStrategy implements SerializableStrategy {

    private FileOutputStream libraryFO;
    private XMLEncoder libraryEncoder;

    private FileInputStream libraryFI;
    private XMLDecoder libraryDecoder;

    private FileOutputStream playlistFO;
    private XMLEncoder playlistEncoder;

    private FileInputStream playlistFI;
    private XMLDecoder playlistDecoder;

    @Override
    public void openWritableLibrary() throws IOException {
        libraryFO = new FileOutputStream("library.xml");
        libraryEncoder = new XMLEncoder(libraryFO);
    }

    @Override
    public void openReadableLibrary() throws IOException {
        libraryFI = new FileInputStream("library.xml");
        libraryDecoder = new XMLDecoder(libraryFI);
    }

    @Override
    public void openWritablePlaylist() throws IOException {
        playlistFO = new FileOutputStream("playlist.xml");
        playlistEncoder = new XMLEncoder(playlistFO);
    }

    @Override
    public void openReadablePlaylist() throws IOException {
        playlistFI = new FileInputStream("playlist.xml");
        playlistDecoder = new XMLDecoder(playlistFI);
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
        libraryEncoder.writeObject(p);
    }

    @Override
    public Playlist readLibrary() throws IOException, ClassNotFoundException {
        return (Playlist) libraryDecoder.readObject();
    }

    @Override
    public void writePlaylist(Playlist p) throws IOException {
        playlistEncoder.writeObject(p);
    }

    @Override
    public Playlist readPlaylist() throws IOException, ClassNotFoundException {
        return (Playlist) playlistDecoder.readObject();
    }



    @Override
    public void closeWritableLibrary() {

        try {
            if(libraryEncoder != null){
                libraryEncoder.flush();
                libraryEncoder.close();
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
            if(libraryDecoder != null){
                libraryDecoder.close();
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
            if(playlistEncoder != null){
                playlistEncoder.flush();
                playlistEncoder.close();
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
            if(playlistDecoder != null){
                playlistDecoder.close();
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