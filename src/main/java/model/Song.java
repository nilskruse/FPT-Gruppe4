package model;

import helper.StringPropertyValueHandler;
import javafx.beans.property.SimpleStringProperty;
import org.apache.openjpa.persistence.Persistent;
import org.apache.openjpa.persistence.jdbc.Strategy;

import java.io.*;
import java.util.ArrayList;

public class Song implements interfaces.Song,Serializable,Externalizable
{
    private static final long serialVersionUID = 4256245623546L;


    private transient SimpleStringProperty path = new SimpleStringProperty();

    private transient SimpleStringProperty title = new SimpleStringProperty();

    private transient SimpleStringProperty album = new SimpleStringProperty();

    private transient SimpleStringProperty interpret = new SimpleStringProperty();


    private long id;

    public Song(){

    }

    public Song(String title,String album, String interpret, String path, long id){
        this.setTitle(title);
        this.setAlbum(album);
        this.setInterpret(interpret);
        this.setPath(path);
        this.id = id;
    }


    public void setAlbum(String album) {
        this.album.set(album);
    }

    public String getAlbum(){
        return album.get();
    }


    public void setInterpret(String interpret) {
        this.interpret.set(interpret);
    }

    public String getInterpret() {
        return interpret.get();
    }


    public void setPath(String path) {
        this.path.set(path);
    }

    public String getPath() {
        return path.get();
    }


    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getTitle() {
        return title.get();
    }



    public SimpleStringProperty albumProperty() {
        return album;
    }

    public SimpleStringProperty interpretProperty() {
        return interpret;
    }

    public SimpleStringProperty pathProperty() {
        return path;
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public String toString(){
        if(id < 10){
            return "0" + id + " " + getTitle() + " " + getAlbum() + " " + getInterpret();
        }else {
            return id + " " + getTitle() + " " + getAlbum() + " " + getInterpret();
        }
    }


    //
    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @Override
    public void writeExternal(ObjectOutput s) throws IOException {
        s.writeObject(title.getValue());
        s.writeObject(album.getValue());
        s.writeObject(interpret.getValue());
        s.writeObject(path.getValue());
        s.writeLong(id);
    }

    @Override
    public void readExternal(ObjectInput s) throws IOException, ClassNotFoundException {
        title = new SimpleStringProperty((String) s.readObject());
        album = new SimpleStringProperty((String) s.readObject());
        interpret = new SimpleStringProperty((String) s.readObject());
        path = new SimpleStringProperty((String) s.readObject());
        id = s.readLong();
    }
}