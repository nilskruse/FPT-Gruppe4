package main;

import controller.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Model;
import sockets.TCPServer;
import sockets.UDPServer;
import view.View;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;

public class Server extends Application {

    public static void main(String[] args) {
        Application.launch((args));
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        // hier die Daten verwalten
        Model model = new Model();
        View view = new View();
        Controller controller = new Controller();

        LocateRegistry.createRegistry(1099);

        Remote rs = controller;
        Naming.rebind("//localhost:1099/controller", rs);
        System.out.println("Server has started...");
        controller.link(model, view);


        // JavaFX new
        Scene scene  = new Scene(view, 700, 500);
        primaryStage.setTitle("MUSICPLAYER");
        primaryStage.setScene(scene);
        primaryStage.show();


        //Starten?? Als Threads?
        UDPServer udpServer = new UDPServer(controller);
        TCPServer tcpServer = new TCPServer(controller);

    }
}

