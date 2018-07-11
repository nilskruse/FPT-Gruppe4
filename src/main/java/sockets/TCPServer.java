package sockets;

import controller.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

    public TCPServer(Controller controller) {
        try (ServerSocket server = new ServerSocket(5020)) {

            while (true) {
                try (Socket client = server.accept()) {
                    ClientThread ct = new ClientThread(client, controller);
                    Thread t1 = new Thread(ct);
                    t1.start();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
