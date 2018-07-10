package sockets;

import interfaces.Controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPServer {

    public UDPServer (Controller sc) {
        // Socket erstellen unter dem der Server erreichbar ist
        try (DatagramSocket socket =  new DatagramSocket(5000);){
            while (true) {
                // Neues Paket anlegen
                DatagramPacket packet = new DatagramPacket(new byte[5], 5);
                // Auf Paket warten
                try {
                    socket.receive(packet);
                    //Thread!!!!!!!!!!!!
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

