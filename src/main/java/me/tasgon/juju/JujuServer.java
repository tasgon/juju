package me.tasgon.juju;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class JujuServer implements JujuServerInterface {

    ScriptEngineManager mgr = new ScriptEngineManager();
    ScriptEngine engine = mgr.getEngineByName("nashorn");

    @Override
    public String exec(String msg) {
        try {
            System.out.println("Received: " + msg);
            Object ret = engine.eval(msg);
            return ret.toString();
        } catch (Exception e) {
            e.printStackTrace();
            StringWriter out = new StringWriter();
            e.printStackTrace(new PrintWriter(out));
            return out.toString();
        }
    }
}
