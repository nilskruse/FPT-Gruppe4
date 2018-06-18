package controller;

import interfaces.Playlist;
import interfaces.SerializableStrategy;
import interfaces.Song;
import model.Model;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;

public class JDBCStrategy implements SerializableStrategy {
    Model model;
    public JDBCStrategy(Model model){
        this.model = model;
    }
    private static void createLibrary(Connection con) {
        try (PreparedStatement pstmt = con.prepareStatement("CREATE TABLE IF NOT EXISTS Library (" +
                "id integer PRIMARY KEY, " +
                "title text, " +
                "interpret text, " +
                "album text, " +
                "path text);")) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createPlaylist(Connection con) {
        try (PreparedStatement pstmt = con.prepareStatement("CREATE TABLE IF NOT EXISTS Playlist (" +
                "id integer);")) {
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
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:musicplayer.db")) {

            createLibrary(con);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:musicplayer.db");
             PreparedStatement pstmt = con.prepareStatement("INSERT INTO Library (title,interpret,album,path) VALUES (?,?,?,?)");
             PreparedStatement query = con.prepareStatement("SELECT * FROM Library WHERE path=?");
             PreparedStatement update = con.prepareStatement("UPDATE Library SET title = ?, interpret = ?, album = ? WHERE path = ?")) {

            for(Song s : p){
                query.setString(1,s.getPath());
                ResultSet rs = query.executeQuery();
                if(!rs.next()) {
                    pstmt.setString(1, s.getTitle());
                    pstmt.setString(2, s.getInterpret());
                    pstmt.setString(3, s.getAlbum());
                    pstmt.setString(4, s.getPath());
                    pstmt.executeUpdate();
                    System.out.println("insert");
                }else{
                    update.setString(1, s.getTitle());
                    update.setString(2, s.getInterpret());
                    update.setString(3, s.getAlbum());
                    update.setString(4, s.getPath());
                    update.executeUpdate();
                    System.out.println("update");

                }


            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Playlist readLibrary() throws IOException, ClassNotFoundException {
        Playlist returnLib = new model.Playlist();
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:musicplayer.db");
             PreparedStatement pstmt = con.prepareStatement("SELECT id,title,interpret,album,path FROM Library");
             ResultSet rs = pstmt.executeQuery()) {

            while(rs.next()){
                returnLib.addSong(new model.Song(rs.getString("title"),rs.getString("interpret"),rs.getString("album"),rs.getString("path"),(long)rs.getInt("id")));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnLib;
    }

    @Override
    public void writePlaylist(Playlist p) throws IOException {
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:musicplayer.db")) {

            createPlaylist(con);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:musicplayer.db");
             PreparedStatement pstmt = con.prepareStatement("INSERT INTO Playlist (id) VALUES (?)");
             PreparedStatement drop = con.prepareStatement("DELETE FROM Playlist")) {

            drop.executeUpdate();

            for(Song s : p){
                pstmt.setInt(1,(int)s.getId());
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Playlist readPlaylist() throws IOException, ClassNotFoundException {
        Playlist lib = model.getLibrary();
        Playlist returnPly = new model.Playlist();
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:musicplayer.db");
             PreparedStatement pstmt = con.prepareStatement("SELECT id FROM Playlist");
             ResultSet rs = pstmt.executeQuery()) {

            while(rs.next()){
                returnPly.addSong(lib.findSongByID((long)rs.getInt("id")));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnPly;
    }

    @Override
    public void load(Model model) {

        try {
            model.getLibrary().clearPlaylist();
            model.getLibrary().addAll(readLibrary().getList());
            model.getPlaylist().clearPlaylist();
            model.getPlaylist().addAll(readPlaylist().getList());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(Model model) {
        try {
            writeLibrary(model.getLibrary());
            writePlaylist(model.getPlaylist());
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
