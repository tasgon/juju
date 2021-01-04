package me.tasgon.juju;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class JujuServer extends UnicastRemoteObject implements JujuServerInterface {

    ScriptEngineManager mgr = new ScriptEngineManager();
    ScriptEngine engine = mgr.getEngineByName("nashorn");

    protected JujuServer() throws RemoteException {
        super(0);
    }

    @Override
    public void exec(String msg) {
        try {
            Object ret = engine.eval(msg);

        } catch (ScriptException e) {

        }
    }
}
