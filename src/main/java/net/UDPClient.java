package net;

import controller.Controller;
import javafx.application.Platform;

import java.io.IOException;
import java.net.*;


public class UDPClient implements Runnable{
    private Controller contr;
    private int port;
    public UDPClient (Controller contr, int port) {
        this.contr = contr;
        this.port = port;


    }

    @Override
    public void run() {
        InetAddress ia = null;
        try {
            ia = InetAddress.getByName("localhost");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        try (DatagramSocket dSocket = new DatagramSocket(port)) {

            try {
                while (true) {
                    String command = "TIME:";

                    byte buffer[] = command.getBytes();
                    DatagramPacket packet = new DatagramPacket(buffer,
                            buffer.length, ia, 5000);
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
                    Platform.runLater(() -> contr.setPlayTime(time));

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

