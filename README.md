# JvmDemos
Jvm related tools and demos,like  java class conflict detection, search class in classpath, etc

## how to use
use maven install to get a jar, and then run the main methods as you need, or just run in a IDE.

## conflict detection
Traverse all jars in a folder, find classes that appear in different jars.
 
com.mallow.jvm.tools.ConflictDetector <path>

## class file anyalyzer
print infomation that generated from a class file.

com.mallow.jvm.tools.classanalyzer.ClassFileAnalyzer <path>

## dumpe class 
copied from https://github.com/hengyunabc/dumpclass, generate class files from a running java process.

com.mallow.jvm.tools.dumpclass.DumpMain <pid> <pattern> [outputDir]
