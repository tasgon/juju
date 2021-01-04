package me.tasgon.juju;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Agent {
    public static JujuClientInterface client = null;

    public static void agentmain(String agentArgs, Instrumentation inst) {
        System.out.println("Juju " + Agent.class.getPackage().getImplementationVersion() + " loading.");
        String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        try {
            Registry registry = LocateRegistry.getRegistry();
            System.out.println("locations found:");
            for (String s : registry.list()) System.out.println(s);
            client = (JujuClientInterface) registry.lookup(String.format("//localhost/juju-%s-client", pid));
            JujuServer server = new JujuServer();
            Naming.rebind(String.format("//localhost/juju-%s-server", pid), server);
        } catch (Exception e) {
            System.out.println("Failed to activate juju server!");
            e.printStackTrace();
        }
    }
}
