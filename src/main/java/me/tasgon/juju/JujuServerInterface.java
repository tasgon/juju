package me.tasgon.juju;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface JujuServerInterface extends Remote {
    String exec(String msg) throws RemoteException;
}
