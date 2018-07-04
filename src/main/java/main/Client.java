package main;

import controller.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Model;
import view.View;

import java.rmi.Naming;

public class Client extends Application {
    public static void main(String[] args) throws Exception{
        Application.launch(args);


    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        controller.Controller remote = (controller.Controller) Naming.lookup("//localhost/musicplayer");
        Model model = new Model();
        View view = new View(remote);
        remote.link(model, view);
        Scene scene  = new Scene(view, 700, 500);
        primaryStage.setTitle("MUSICPLAYER");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
