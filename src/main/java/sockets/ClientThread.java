package sockets;

import controller.Controller;

import java.io.*;
import java.net.Socket;

public class ClientThread implements Runnable {

    private Socket client;
    private Controller controller;

    public ClientThread(Socket client, Controller controller) {
        this.client = client;
        this.controller = controller;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            OutputStream out = client.getOutputStream();

            String clientName = in.readLine();
            String password = in.readLine();

            if(password.equals("1234")) {
                //Was muss jetzt passieren??
            }

            out.flush();
            client.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

