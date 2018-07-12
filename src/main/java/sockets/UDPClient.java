package sockets;

import controller.Controller;
import javafx.application.Platform;

import java.io.IOException;
import java.net.*;


public class UDPClient {

    public UDPClient (Controller controller) {
        // Eigene Adresse erstellen
        InetAddress ia = null;
        try {
            ia = InetAddress.getByName("localhost");
        } catch (UnknownHostException e2) {
            e2.printStackTrace();
        }
        // Socket für den Klienten anlegen
        try (DatagramSocket dSocket = new DatagramSocket(5555)) {

            try {
                while (true) {
                    String command = "TIME:";

                    byte buffer[] = command.getBytes();

                    // Paket mit der Anfrage vorbereiten
                    DatagramPacket packet = new DatagramPacket(buffer,
                            buffer.length, ia, 5020);
                    // Paket versenden
                    dSocket.send(packet);

                    byte answer[] = new byte[1024];
                    packet = new DatagramPacket(answer, answer.length);
                    try {
                        dSocket.receive(packet);
                    } catch (SocketTimeoutException | NullPointerException  e) {
                        dSocket.send(packet);
                        continue;
                    }

                    String time = new String(packet.getData());
                    System.out.println(time);
                   // ausgabe der Playtime
                    // controller.setPlayTime(time);
                        System.out.println("controller Set Playtime"+time);
                    //tbd: Methode - setPlayTime()
                    Platform.runLater(() -> controller.setPlayTime(time));

                    Thread.sleep(1000);

                }
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } catch (SocketException e1) {
            e1.printStackTrace();
        }

    }

}

