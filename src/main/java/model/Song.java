package model;

import helper.StringPropertyValueHandler;
import javafx.beans.property.SimpleStringProperty;
import org.apache.openjpa.persistence.Persistent;
import org.apache.openjpa.persistence.jdbc.Strategy;

import javax.persistence.*;
import java.io.*;
import java.util.ArrayList;

@Entity()
@Table( name = "Library" )
public class Song implements interfaces.Song,Serializable,Externalizable
{
    private static final long serialVersionUID = 4256245623546L;


    @Id
    @Column(name="id")
    private long id;

    @Persistent
    @Strategy("helper.StringPropertyValueHandler")
    @Column(name ="title")
    private SimpleStringProperty title = new SimpleStringProperty();

    @Persistent
    @Strategy("helper.StringPropertyValueHandler")
    @Column(name ="album")
    private SimpleStringProperty album = new SimpleStringProperty();

    @Persistent
    @Strategy("helper.StringPropertyValueHandler")
    @Column(name ="interpret")
    private SimpleStringProperty interpret = new SimpleStringProperty();

    @Persistent
    @Strategy("helper.StringPropertyValueHandler")
    @Column(name ="path")
    private SimpleStringProperty path = new SimpleStringProperty();


    public Song(){

    }

    public Song(String title,String album, String interpret, String path, long id){
        this.setTitle(title);
        this.setAlbum(album);
        this.setInterpret(interpret);
        this.setPath(path);
        this.id = id;
    }




    public String getAlbum(){
        return album.get();
    }
    public void setAlbum(String album) {
        this.album.set(album);
    }

    public void setInterpret(String interpret) {
        this.interpret.set(interpret);
    }

    public String getInterpret() {
        return interpret.get();
    }



    public String getPath() {
        return path.get();
    }
    public void setPath(String path) {
        this.path.set(path);
    }


    public String getTitle() {
        return title.get();
    }
    public void setTitle(String title) {
        this.title.set(title);
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