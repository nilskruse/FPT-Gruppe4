package net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPServer implements Runnable {
    public void run(){
        DatagramSocket socket = null;
        try{
            socket = new DatagramSocket(5000);
            while(true){
                DatagramPacket packet = new DatagramPacket(new byte[10],10);

                try{
                    socket.receive(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }finally {
            socket.close();
        }
    }
}
class TimeUDPThread extends Thread{

    private DatagramPacket packet;
    private DatagramSocket socket;

    public TimeUDPThread(DatagramPacket packet, DatagramSocket socket)
            throws SocketException {
        this.packet = packet;
        this.socket = socket;
    }
}