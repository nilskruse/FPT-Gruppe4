package sockets;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    public static void main(String[] args) {

        // ServerSocket erstellen
        try (ServerSocket server = new ServerSocket(3141);) {
            int connections = 0;
            // Timeout nach 1 Minute
            // server.setSoTimeout(60000);
            while (true) {
                try {
                    Socket socket = server.accept();
                    connections++;
                    new TCPServerThread(connections, socket).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }
}
