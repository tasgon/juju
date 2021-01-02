package me.tasgon.juju;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;

public class Main {
    public static void main(String[] args) throws IOException, AttachNotSupportedException, URISyntaxException, AgentLoadException, AgentInitializationException {
        String pid = args[0];
        System.out.println("Attaching to " + pid + " with " + Main.getJarFile());

        VirtualMachine vm = VirtualMachine.attach(pid);
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
