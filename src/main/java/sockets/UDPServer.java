package sockets;
import java.io.IOException;
        import java.net.DatagramPacket;
        import java.net.DatagramSocket;
        import java.net.SocketException;
import controller.Controller;


public class UDPServer {



    public UDPServer(Controller contr  ) {
        // Socket erstellen unter dem der Server erreichbar ist
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(3431);
            while (true) {
                // Neues Paket anlegen
                DatagramPacket packet = new DatagramPacket(new byte[5], 5);
                // Auf Paket warten
                try {
                    socket.receive(packet);
                    // Empfangendes Paket in einem neuen Thread abarbeiten
                    new UDPServerThread(packet, socket, contr).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }

    }

}



