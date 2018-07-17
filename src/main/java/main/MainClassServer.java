package main;


import controller.Controller;
import net.RMIServer;
import controller.ServerController;
import interfaces.ServerRemote;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Model;
import net.SyncedArrayList;
import net.TCPServer;
import net.UDPServer;
import view.View;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;

public class MainClassServer extends Application{
    public static void main(String[] args) {
        Application.launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        SyncedArrayList<String> clientlist = new SyncedArrayList<>();
        

        // hier die Daten verwalten
        Model model = new Model();

        View view = new View();


        Controller controller = new ServerController(clientlist);
        controller.link(model, view);

        TCPServer tcpserver = new TCPServer("server1","abc",clientlist);
        Thread t2 = new Thread(tcpserver);
        t2.start();

        LocateRegistry.createRegistry(1099);

        ServerRemote rmiserver = new RMIServer(controller,clientlist);
        Naming.rebind("server1",rmiserver);

        UDPServer server = new UDPServer(controller);
        Thread t1 = new Thread(server);
        t1.start();


        // JavaFX new
        Scene scene  = new Scene(view, 700, 500);
        primaryStage.setTitle("MUSICPLAYER server");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}
