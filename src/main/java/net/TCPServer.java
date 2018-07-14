package net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class TCPServer implements Runnable{
    private String password;
    private ArrayList<String> clientlist;
    private String servername;

    public TCPServer(String servername,String password, ArrayList<String> clientlist){
        this.servername = servername;
        this.password = password;
        this.clientlist = clientlist;
    }
    public void run() {

        // ServerSocket erstellen
        try (ServerSocket server = new ServerSocket(5020)) {
            int connections = 0;

            while (true) {
                try {
                    Socket socket = server.accept();
                    connections++;
                    new TCPServerThread(connections,servername, socket, password, clientlist).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }
}
