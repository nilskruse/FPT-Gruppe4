package main;


import controller.SyncedArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Model;
import sockets.TCPClient;
import sockets.UDPClient;
import sockets.UDPServer;
import view.View;
import controller.Controller;

public class MainClassClient extends Application{
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

        TCPClient tcpclient = new TCPClient("client1","abc");
        System.out.println(tcpclient.connect());

        // JavaFX new
        Scene scene  = new Scene(view, 700, 500);
        primaryStage.setTitle("MUSICPLAYER client");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}
