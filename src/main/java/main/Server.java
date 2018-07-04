package main;

import java.rmi.Naming;
import java.rmi.Remote;

public class Server {
    public static void main(String[] args) throws Exception{
        Remote remote = new controller.Controller();

        Naming.rebind("musicplayer", remote);
        System.out.println("Server started...");

    }
}
