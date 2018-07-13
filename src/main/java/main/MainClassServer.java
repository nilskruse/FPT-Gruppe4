package main;


import controller.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Model;
import sockets.TCPServer;
import sockets.UDPServer;
import view.View;

import java.util.ArrayList;

public class MainClassServer extends Application{
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


        UDPServer server = new UDPServer(controller);
        Thread t1 = new Thread(server);
        t1.start();


        ArrayList<String> clientlist = new ArrayList<>();

        TCPServer tcpserver = new TCPServer("server1","abc",clientlist);
        Thread t2 = new Thread(tcpserver);
        t2.start();

        // JavaFX new
        Scene scene  = new Scene(view, 700, 500);
        primaryStage.setTitle("MUSICPLAYER server");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}
