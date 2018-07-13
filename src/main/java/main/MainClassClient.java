package main;


import controller.Client;
import controller.ClientController;
import controller.SyncedArrayList;
import interfaces.ClientRemote;
import interfaces.ServerRemote;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Model;
import sockets.TCPClient;
import sockets.UDPClient;
import sockets.UDPServer;
import view.View;
import controller.Controller;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class MainClassClient extends Application{
    public static void main(String[] args) {
        Application.launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        

        // hier die Daten verwalten
        Model model = new Model();
        View view = new View();

        LocateRegistry.createRegistry(1100);

        TCPClient tcpclient = new TCPClient("client1","abc");

        ClientRemote rmiclient = new Client(view);
        Naming.rebind("client1",rmiclient);

        String servicename = tcpclient.connect();

        ServerRemote rmis = (ServerRemote) Naming.lookup("//localhost/" + servicename);


        Controller controller = new ClientController(rmis);
        controller.link(model, view);


        UDPClient client = new UDPClient(controller);
        Thread t1 = new Thread(client);
        t1.start();

        rmis.update();

        // JavaFX new
        Scene scene  = new Scene(view, 700, 500);
        primaryStage.setTitle("MUSICPLAYER client");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}
