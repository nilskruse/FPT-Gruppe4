package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Model;
import model.Song;
import view.View;
import controller.Controller;

import java.io.File;
import java.io.IOException;

public class MainClass extends Application{
    public static void main(String[] args) {
        Application.launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        // hier die Daten verwalten
        Model model = new Model();

        View view = new View();


        Controller controller = new Controller();
        controller.link(model, view);
        addSongsFromFolder(model);

        // JavaFX new
        Scene scene  = new Scene(view, 700, 500);
        primaryStage.setTitle("MUSICPLAYER");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void addSongsFromFolder(Model model){
        long id = model.getLibrary().size();
        // initialize File object
        File file = new File("songs");

        // check if the specified pathname is directory first
        if(file.isDirectory()){
            //list all files on directory
            File[] files = file.listFiles();
            for(File f:files){
                try {
                    model.getLibrary().addSong(new Song(f.getName(),"","",f.getCanonicalPath(),id));
                    id++;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
