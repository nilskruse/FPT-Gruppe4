package main;

import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Model;
import sockets.UDPServer;
import view.View;

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

       // LocateRegistry.createRegistry(1099);

       // Remote rs = controller;
       // Naming.rebind("//localhost:1099/controller", rs);

        controller.link(model, view);
        System.out.println("Server has started...");

        // Server hat keien Oberfläche startet Legentlich als CLI
         //JavaFX new
        //Scene scene  = new Scene(view, 700, 500);
       // primaryStage.setTitle("MUSICPLAYER");
       // primaryStage.setScene(scene);
      //  primaryStage.show();


        //Starten?? Als Threads?
        UDPServer udpserver = new UDPServer(controller);

        //TCPServer tcpServer = new TCPServer(controller);


    }
}

