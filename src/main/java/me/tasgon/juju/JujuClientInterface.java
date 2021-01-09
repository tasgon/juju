package me.tasgon.juju;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface JujuClientInterface extends Remote {
    void print(String msg) throws RemoteException;
    //void initLoop() throws RemoteException;
    void serverReady() throws RemoteException;
}
