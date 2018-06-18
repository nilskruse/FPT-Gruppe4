package controller;

import interfaces.Playlist;
import interfaces.SerializableStrategy;
import interfaces.Song;
import model.Model;
import org.apache.openjpa.persistence.OpenJPAPersistence;

import javax.persistence.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;


public class OpenJPAStrategy implements SerializableStrategy {
    Model model;

    @Override
    public void openWritableLibrary() throws IOException {

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

    private static void createLibrary(Connection con) {
        try (PreparedStatement pstmt = con.prepareStatement("CREATE TABLE IF NOT EXISTS Library (" +
                "id integer, " +
                "title text, " +
                "album text, " +
                "interpret text, " +
                "path text);");) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (PreparedStatement pstmt = con.prepareStatement("CREATE TABLE IF NOT EXISTS Library (" +
                "id integer, " +
                "title text, " +
                "album text, " +
                "interpret text, " +
                "path text);");PreparedStatement drop = con.prepareStatement("DELETE FROM Library")) {
            drop.executeUpdate();
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeLibrary(Playlist p) throws IOException {
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:openjpa.db")) {
            createLibrary(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        EntityManagerFactory fac = getWithoutConfig();
        EntityManager e = fac.createEntityManager();
        EntityTransaction t = e.getTransaction();
        t.begin();
        for(Song s : p){
            e.persist(s);
        }
        t.commit();

        e.close();
        fac.close();
    }

    @Override
    public Playlist readLibrary() throws IOException, ClassNotFoundException {
        Playlist returnLib = new model.Playlist();
        EntityManagerFactory fac = getWithoutConfig();
        EntityManager e = fac.createEntityManager();
        EntityTransaction t = e.getTransaction();

        t.begin();

        for(Object o : e.createQuery("SELECT x FROM Song x").getResultList()){
            Song s = (model.Song) o;
            returnLib.addSong(s);
        }
        System.out.println(returnLib);

        t.commit();

        e.close();
        fac.close();
        return returnLib;

    }

    @Override
    public void writePlaylist(Playlist p) throws IOException {
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:openjpapl.db")) {
            createLibrary(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        EntityManagerFactory fac = getWithoutConfigPl();
        EntityManager e = fac.createEntityManager();
        EntityTransaction t = e.getTransaction();
        t.begin();
        System.out.println(p);
        int i = 0;
        for(Song s : p){
            e.persist(new model.Song(s.getTitle(),s.getAlbum(),s.getInterpret(),s.getPath(),s.getId()));
            System.out.println(++i);
        }
        t.commit();

        e.close();
        fac.close();
    }

    @Override
    public Playlist readPlaylist() throws IOException, ClassNotFoundException {
        Playlist lib = new model.Playlist();
        Playlist returnPl = new model.Playlist();
        EntityManagerFactory fac = getWithoutConfigPl();
        EntityManager e = fac.createEntityManager();
        EntityTransaction t = e.getTransaction();

        t.begin();

        for(Object o : e.createQuery("SELECT x FROM Song x").getResultList()){
            Song s = (model.Song) o;
            lib.addSong(s);
        }

        t.commit();

        e.close();
        fac.close();
        for(Song s : lib){
            returnPl.addSong(model.getLibrary().findSongByID(s.getId()));
        }
        return returnPl;
    }



    @Override
    public void load(Model model) {
        this.model = model;
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

    public static EntityManagerFactory getWithoutConfig() {

        Map<String, String> map = new HashMap<String, String>();

        map.put("openjpa.ConnectionURL","jdbc:sqlite:openjpa.db");
        map.put("openjpa.ConnectionDriverName", "org.sqlite.JDBC");
        map.put("openjpa.RuntimeUnenhancedClasses", "supported");
        map.put("openjpa.jdbc.SynchronizeMappings", "false");

        // find all classes to registrate them
        List<Class<?>> types = new ArrayList<Class<?>>();
        types.add(model.Song.class);

        if (!types.isEmpty()) {
            StringBuffer buf = new StringBuffer();
            for (Class<?> c : types) {
                if (buf.length() > 0)
                    buf.append(";");
                buf.append(c.getName());
            }
            // <class>Pizza</class>
            map.put("openjpa.MetaDataFactory", "jpa(Types=" + buf.toString()+ ")");
        }

        return OpenJPAPersistence.getEntityManagerFactory(map);

    }
    public static EntityManagerFactory getWithoutConfigPl() {

        Map<String, String> map = new HashMap<String, String>();

        map.put("openjpa.ConnectionURL","jdbc:sqlite:openjpapl.db");
        map.put("openjpa.ConnectionDriverName", "org.sqlite.JDBC");
        map.put("openjpa.RuntimeUnenhancedClasses", "supported");
        map.put("openjpa.jdbc.SynchronizeMappings", "false");

        // find all classes to registrate them
        List<Class<?>> types = new ArrayList<Class<?>>();
        types.add(model.Song.class);

        if (!types.isEmpty()) {
            StringBuffer buf = new StringBuffer();
            for (Class<?> c : types) {
                if (buf.length() > 0)
                    buf.append(";");
                buf.append(c.getName());
            }
            // <class>Pizza</class>
            map.put("openjpa.MetaDataFactory", "jpa(Types=" + buf.toString()+ ")");
        }

        return OpenJPAPersistence.getEntityManagerFactory(map);

    }
}
