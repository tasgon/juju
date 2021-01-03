package me.tasgon.juju;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.lang.instrument.Instrumentation;

public class Agent {
    public static void agentmain(String agentArgs, Instrumentation inst) {
        System.out.println("Juju " + Agent.class.getPackage().getImplementationVersion() + " loading.");
    }
}
