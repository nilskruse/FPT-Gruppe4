package net;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClient {

    private String clientName;
    private String password;

    public TCPClient( String clientName, String password) {
        this.clientName = clientName;
        this.password = password;
    }
    public String connect(){
        try (Socket server = new Socket("localhost", 5020);
             DataInputStream in = new DataInputStream(server.getInputStream());
             DataOutputStream out = new DataOutputStream(server.getOutputStream())) {


            out.writeUTF(clientName);
            out.writeUTF(password);

            out.flush();

            String result = in.readUTF();
            return result;


        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return null;
    }
}