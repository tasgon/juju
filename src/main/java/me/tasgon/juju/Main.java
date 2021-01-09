package me.tasgon.juju;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collections;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static JujuServerInterface server = null;
    public static String targetPID = null;

    public static ExecutorService executor = Executors.newSingleThreadExecutor();

    public static Registry registry;

    public static void main(String[] args) throws IOException, AttachNotSupportedException, URISyntaxException, AgentLoadException, AgentInitializationException, AlreadyBoundException {
        String pid = args[0];
        Main.targetPID = pid;
        System.out.println("Attaching to " + pid + " with " + Main.getJarFile());

        VirtualMachine vm = VirtualMachine.attach(pid);
        JujuClient client = new JujuClient();
        registry = LocateRegistry.createRegistry(17320);
        registry.rebind(String.format("//localhost/juju-%s-client", pid), (JujuClientInterface) UnicastRemoteObject.exportObject(client, 0));
        System.out.println("Server initialized");
        try {
            vm.loadAgent(Main.getJarFile());
        } finally {
            vm.detach();
        }
    }

    public static String getJarFile() throws URISyntaxException {
        return new File(Main.class.getProtectionDomain().getCodeSource().getLocation()
                .toURI()).getPath();
    }

}
