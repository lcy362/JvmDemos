package com.mallow.jvm.tools;

import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;

/**
 * Created by lcy on 2017/3/7.
 */
public class ConflictDetector extends ClasspathTraverser {
    private Map<String, String> existClasses;
    private String path;
    public ConflictDetector(String path) {
        this.path = path;
        existClasses = new HashMap<String, String>();
    }
    @Override
    protected void handleOneJar(JarEntry jarEntry, String jarName) {
        String entryName = jarEntry.getName();
        if (existClasses.containsKey(entryName)) {
            existClasses.put(entryName, existClasses.get(entryName) + "," + jarName);
            System.out.println("detect conflict for class: " + entryName + " in jars " + existClasses.get(entryName));
        } else {
            existClasses.put(entryName, jarName);
        }
    }

    @Override
    public void execute() {
        traverseJars(path);
    }

    public static void main(String args[]) {
        String input;
        if (args.length >= 1) {
            input = args[0];
        } else {
            input = "D://lib";
        }
        ConflictDetector detector = new ConflictDetector(input);
        detector.execute();
    }
}
