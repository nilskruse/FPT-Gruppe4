package main;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Model;
import sockets.UDPClient;
import sockets.UDPServer;
import view.View;
import controller.Controller;

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

        UDPClient client = new UDPClient(controller);
        Thread t1 = new Thread(client);
        t1.start();


        // JavaFX new
        Scene scene  = new Scene(view, 700, 500);
        primaryStage.setTitle("MUSICPLAYER client");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}
