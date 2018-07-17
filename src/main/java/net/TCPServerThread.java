package net;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class TCPServerThread extends Thread {
    private int name;
    private Socket socket;
    private ArrayList<String> clientlist;
    private String password,servername;

    public TCPServerThread(int name,String servername, Socket socket,String password,ArrayList<String> clientlist) {
        this.servername = servername;
        this.name = name;
        this.socket = socket;
        this.clientlist = clientlist;
        this.password = password;
    }

    public void run() {
        String msg = "Server: Verbindung " + name;
        System.out.println(msg + " hergestellt");

        try (DataInputStream in = new DataInputStream(socket.getInputStream());
             DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {

            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            String name = in.readUTF();
            String pass = in.readUTF();
            System.out.println(name + ":" + pass);

            if(pass.equals(password)){
                clientlist.add(name);
                out.writeUTF(servername);
                out.flush();
                System.out.println(name + " authentifiziert!");
            }



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