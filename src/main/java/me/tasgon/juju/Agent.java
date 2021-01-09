package me.tasgon.juju;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Agent {
    public static JujuClientInterface client = null;

    public static void agentmain(String agentArgs, Instrumentation inst) {
        System.out.println("Juju " + Agent.class.getPackage().getImplementationVersion() + " loading.");
        String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 17320);
            System.out.println("locations found:");
            for (String s : registry.list()) System.out.println(s);
            JujuClientInterface client = (JujuClientInterface) registry.lookup(String.format("//localhost/juju-%s-client", pid));
            //for (Method m : obj.getClass().getDeclaredMethods()) System.out.println(m.getName());
            JujuServer server = new JujuServer();
            registry.bind(String.format("//localhost/juju-%s-server", pid), (JujuServerInterface) UnicastRemoteObject.exportObject(server, 0));
            client.serverReady();
        } catch (Exception e) {
            System.out.println("Failed to activate juju server!");
            e.printStackTrace();
        }
    }
}
