package net;

import controller.Controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
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
        InetAddress address = packet.getAddress();
        int port = packet.getPort();
        int len = packet.getLength();
        byte[] data = packet.getData();

        //System.out.println("Anfrage von "+address+" vom Port "+port+" mit der Länge "+len+"\n"+new String(data));

        String da = new String(packet.getData());

        try (Scanner sc = new Scanner(da).useDelimiter(":")) {
            String keyword = sc.next();

            if (keyword.equals("TIME")) {
                // hier wird die zeit in ein Paket gepackt
               // String time =  contr.getPlayTime();
                String time = contr.getPlayTime();
                if(time == null){
                    time = "00:00 / 00:00";
                }
                byte[] myTime = time.getBytes();

                packet = new DatagramPacket(myTime, myTime.length, address, port);

                try {
                             socket.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                byte[] nothing = new byte[1024];
                nothing = new String("Command unknown").getBytes();
                try {
                    sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                packet = new DatagramPacket(nothing, nothing.length, address, port);
                try {
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