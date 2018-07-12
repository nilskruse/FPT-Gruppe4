package sockets;

import controller.Controller;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClient {

    public TCPClient(Controller controller, String clientName, String password) {

        try (Socket server = new Socket("localhost", 5020);
             InputStream in = server.getInputStream();
             PrintWriter out = new PrintWriter(new OutputStreamWriter(server.getOutputStream()));) {

            out.write(clientName);
            out.write(password);

            out.flush();

            int result = in.read();

            //Was muss jetzt passieren??


        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}