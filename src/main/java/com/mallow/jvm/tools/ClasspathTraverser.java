package com.mallow.jvm.tools;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by lcy on 2017/3/7.
 */
public abstract class ClasspathTraverser {
    protected void traverseJars(String beginPath) {
        File dir = new File(beginPath);
        File[] files = dir.listFiles();
        for (File file : files) {
            JarFile oneJar = null;
            try {
               oneJar = new JarFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (oneJar != null) {
                String jarName = oneJar.getName();
                Enumeration<JarEntry> enumeration = oneJar.entries();
                while (enumeration.hasMoreElements()) {
                    JarEntry jarEntry = enumeration.nextElement();
                    if (jarEntry.getName().endsWith(".class")) {
                        handleOneJar(jarEntry, jarName);
                    }
                }
            }
        }
    }

    protected abstract void handleOneJar(JarEntry jarEntry, String jarName);

    protected abstract void execute();
}
