package me.tasgon.juju;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class JujuClient implements JujuClientInterface {
    /*protected JujuClient() throws RemoteException {
        super(0);
    }*/

    @Override
    public void print(String msg) {
        System.out.println(msg);
    }

    public void initLoop() {
        Main.executor.execute(() -> {
            while (true) {
                System.out.print(">> ");
                Scanner sc = new Scanner(System.in);
                try {
                    String ret = Main.server.exec(sc.nextLine());
                    System.out.println(ret);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void serverReady() {
        System.out.println("Client initialized");
        try {
            Main.server = (JujuServerInterface) Main.registry.lookup(String.format("//localhost/juju-%s-server", Main.targetPID));
            this.initLoop();
        } catch (Exception e) {
            System.out.println("Failed to connect to server!");
            e.printStackTrace();
        }
    }
}
