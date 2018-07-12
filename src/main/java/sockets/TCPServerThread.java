package sockets;

import com.oracle.tools.packager.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.Socket;

public class TCPServerThread extends Thread {
    private int name;
    private Socket socket;

    public TCPServerThread(int name, Socket socket) {
        this.name = name;
        this.socket = socket;
    }

    public void run() {
        String msg = "EchoServer: Verbindung " + name;
        System.out.println(msg + " hergestellt");
        StringWriter writer = new StringWriter();

        try (InputStream in = socket.getInputStream();
             OutputStream out = socket.getOutputStream()) {
            try {
                sleep((long) (Math.random() * 10000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String a = String.valueOf(in.read());
            String b = String.valueOf(in.read());



            // Ergebnis auf Outputstream schreiben
            out.write(a+b);
            out.flush();

            System.out.println("GGT von "+a+" und "+b+" ist "+a+"b:"+b);
            System.out.println("Verbindung " + name + " wird beendet");

        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}