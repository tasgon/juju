package me.tasgon.juju;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class JujuClient extends UnicastRemoteObject implements JujuClientInterface {
    protected JujuClient() throws RemoteException {
        super(0);
    }

    @Override
    public void print(String msg) {
        System.out.println(msg);
    }

    @Override
    public void processObject(Object obj) {
        if (obj != null) System.out.println(obj.toString());
        Main.executor.execute(() -> {
            System.out.print(">> ");
            Scanner sc = new Scanner(System.in);
            Main.server.exec(sc.nextLine());
        });
    }

    @Override
    public void serverReady() {
        System.out.println("Client initialized");
        try {
            Main.server = (JujuServerInterface) Naming.lookup(String.format("//localhost/juju-%s-server", Main.targetPID));
            this.processObject(null);
        } catch (Exception e) {
            System.out.println("Failed to connect to server!");
            e.printStackTrace();
        }
    }
}
