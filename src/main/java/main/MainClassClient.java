package main;


import controller.ClientController;
import interfaces.ClientRemote;
import interfaces.ServerRemote;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Model;
import net.RMIClient;
import net.TCPClient;
import net.UDPClient;
import view.View;
import controller.Controller;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class MainClassClient extends Application{
    private int clientnumber = 1;
    private String clientname = "client" + clientnumber;
    public static void main(String[] args) {
        Application.launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        

        // hier die Daten verwalten
        Model model = new Model();
        View view = new View();



        TCPClient tcpclient = new TCPClient(clientname,"abc");

        LocateRegistry.createRegistry(1099 + clientnumber);
        ClientRemote rmiclient = new RMIClient(view);
        Naming.rebind(clientname,rmiclient);

        String servicename = tcpclient.connect();

        ServerRemote rmis = (ServerRemote) Naming.lookup("//localhost/" + servicename);


        Controller controller = new ClientController(rmis);
        controller.link(model, view);


        UDPClient client = new UDPClient(controller,5555 + clientnumber);
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
