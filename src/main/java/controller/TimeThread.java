package controller;

import controller.Controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

public class TimeThread implements Runnable {

    private Controller controller;
    private DatagramPacket packet;
    private DatagramSocket socket;

    @Override
    public void run() {
        // Daten auslesen
        InetAddress address = packet.getAddress();
        int port = packet.getPort();
        int len = packet.getLength();
        byte[] data = packet.getData();

        System.out.println("Anfrage von "+address+" vom Port "+port+" mit der Länge "+len+"\n"+new String(data));

        // Nutzdaten in ein Stringobjekt übergeben
        String da = new String(data);
        // Kommandos sollen durch : getrennt werden
        try (Scanner scanner = new Scanner(da).useDelimiter(":")) {
            // Erstes Kommando filtern
            String keyword = scanner.next();

            if (keyword.equals("TIME")) {

                String time = new String();
                //tbd: Methode getTime();
                //time = controller.getTime();
                byte[] myTime =  time.getBytes();

                // Paket mit neuen Daten (Datum) als Antwort vorbereiten
                packet = new DatagramPacket(myTime, myTime.length,
                        address, port);
                // Paket versenden
                socket.send(packet);

            } else {
                byte[] myNothing = null;
                myNothing = new String("Command unknown").getBytes();

                // Paket mit Information, dass das Schlüsselwort ungültig ist als Antwort vorbereiten
                packet = new DatagramPacket(myNothing, myNothing.length,
                        address, port);
                // Paket versenden
                socket.send(packet);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public TimeThread (DatagramPacket packet, DatagramSocket socket, controller.Controller controller) throws SocketException {
        this.packet = packet;
        this.socket = socket;
        this.controller = controller;
    }
}
