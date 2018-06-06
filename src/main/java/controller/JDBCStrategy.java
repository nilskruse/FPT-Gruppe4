package controller;

import interfaces.Playlist;
import interfaces.SerializableStrategy;
import interfaces.Song;
import model.Model;

import java.io.IOException;
import java.sql.*;

public class JDBCStrategy implements SerializableStrategy {

    private static void createLibrary(Connection con) {
        try (PreparedStatement pstmt = con.prepareStatement("CREATE TABLE IF NOT EXISTS Library (" +
                "id integer PRIMARY KEY, " +
                "title text, " +
                "artist text, " +
                "path text);")) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createPlaylist(Connection con) {
        try (PreparedStatement pstmt = con.prepareStatement("CREATE TABLE IF NOT EXISTS Playlist (" +
                "id integer PRIMARY KEY, " +
                "title text, " +
                "artist text, " +
                "path text);")) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void openWritableLibrary() throws IOException {
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:musicplayer.db")) {
            createLibrary(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void openReadableLibrary() throws IOException {

    }

    @Override
    public void openWritablePlaylist() throws IOException {


    }

    @Override
    public void openReadablePlaylist() throws IOException {

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
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:musicplayer.db");
             PreparedStatement pstmt = con.prepareStatement("INSERT INTO Library (title,artist,path) VALUES (?,?,?)")) {
            createLibrary(con);
            for(Song s : p){
                pstmt.setString(1,s.getTitle());
                pstmt.setString(2,s.getInterpret());
                pstmt.setString(3,s.getPath());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Playlist readLibrary() throws IOException, ClassNotFoundException {
        return null;
    }

    @Override
    public void writePlaylist(Playlist p) throws IOException {

    }

    @Override
    public Playlist readPlaylist() throws IOException, ClassNotFoundException {
        return null;
    }

    @Override
    public void load(Model model) {

    }

    @Override
    public void save(Model model) {
        try {
            writeLibrary(model.getLibrary());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeWritableLibrary() {

    }

    @Override
    public void closeReadableLibrary() {

    }

    @Override
    public void closeWritablePlaylist() {

    }

    @Override
    public void closeReadablePlaylist() {

    }
}
