package controller;

import interfaces.Playlist;
import interfaces.SerializableStrategy;
import interfaces.Song;
import model.Model;
import org.apache.openjpa.persistence.OpenJPAPersistence;

import javax.persistence.*;

import java.io.IOException;
import java.util.*;


public class OpenJPAStrategy implements SerializableStrategy {

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

    @Override
    public void writeLibrary(Playlist p) throws IOException {

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
        try {
            model.getLibrary().clearPlaylist();
            model.getLibrary().addAll(readLibrary().getList());
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
    public static EntityManager getEntityManager ()
    {
        //je nachdem mit oder ohne Konfig
        EntityManager e = getWithConfig().createEntityManager();
        //EntityManager e = getWithoutConfig().createEntityManager();
        return e;
       // return
    }
    private  static EntityManagerFactory getWithConfig()
    {
        return Persistence.createEntityManagerFactory("openjpa");
    }
    // Class zum einlesen ohne Konfig datei
    private static EntityManagerFactory getWithoutConfig()
    {

        Map<String, String> map = new HashMap<String, String>();

        map.put("openjpa.ConnectionURL","jdbc:sqlite:libary.db");
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
