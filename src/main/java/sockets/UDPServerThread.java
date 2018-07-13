package sockets;

import controller.Controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Date;
import java.util.Scanner;

class UDPServerThread extends Thread {

    private DatagramPacket packet;
    private DatagramSocket socket;
    private Controller contr;

    public UDPServerThread(DatagramPacket packet, DatagramSocket socket, Controller contr )
            throws SocketException {
        this.packet = packet;
        this.socket = socket;
        this.contr = contr;
    }

    public void run() {
        // Daten auslesen
        InetAddress address = packet.getAddress();
        int port = packet.getPort();
        int len = packet.getLength();
        byte[] data = packet.getData();

        //System.out.println("Anfrage von "+address+" vom Port "+port+" mit der Länge "+len+"\n"+new String(data));

        // Nutzdaten in ein Stringobjekt übergeben
        String da = new String(packet.getData());
        // Kommandos sollen durch : getrennt werden

       /// gucken wie gesendet wird
        try (Scanner sc = new Scanner(da).useDelimiter(":")) {
            // Erstes Kommando filtern
            String keyword = sc.next();

            if (keyword.equals("TIME")) {
                    // hier wird die zeit in ein Paket gepackt
               // String time =  contr.getPlayTime();
                String time = contr.getPlayTime();
                if(time == null){
                    time = "00:00 / 00:00";
                }
                byte[] myTime = time.getBytes();

                // Paket mit neuen Daten als Antwort vorbereiten
                packet = new DatagramPacket(myTime, myTime.length, address, port);

                try {
                    // Paket versenden
                             socket.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                byte[] myDate = new byte[1024];
                myDate = new String("Command unknown").getBytes();
                try {
                    sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                // Paket mit Information, dass das Schlüsselwort ungültig ist als
                // Antwort vorbereiten
                packet = new DatagramPacket(myDate, myDate.length, address, port);
                try {
                    // Paket versenden
                    socket.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}