package net;
import java.io.IOException;
        import java.net.DatagramPacket;
        import java.net.DatagramSocket;
        import java.net.SocketException;
import controller.Controller;


public class UDPServer implements Runnable{
    private Controller contr;


    public UDPServer(Controller contr  ) {
        this.contr = contr;


    }


    @Override
    public void run() {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(5000);
            while (true) {
                DatagramPacket packet = new DatagramPacket(new byte[5], 5);
                try {
                    socket.receive(packet);
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



