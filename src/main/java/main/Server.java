package main;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;

public class Server {
    public static void main(String[] args) throws Exception{
        Remote remote = new controller.Controller();

        LocateRegistry.createRegistry(1099);

        Naming.rebind("//localhost:1099/musicplayer", remote);
        System.out.println("Server started...");

    }
}
