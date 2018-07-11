package sockets;

import controller.TimeThread;
import controller.Controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPServer {

    public UDPServer (Controller controller) {
        // Socket erstellen unter dem der Server erreichbar ist
        try (DatagramSocket socket =  new DatagramSocket(5000);){
            while (true) {
                // Neues Paket anlegen
                DatagramPacket packet = new DatagramPacket(new byte[5], 5);
                // Auf Paket warten
                try {
                    socket.receive(packet);
                    TimeThread tt = new TimeThread(packet, socket, controller);
                    Thread t1 = new Thread(tt);
                    t1.start();
                    System.out.println("Thread gestartet");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } //finally {socket.close();??
    }
}

