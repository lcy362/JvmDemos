# JvmDemos
Jvm related tools and demos,like  java class conflict detection, search class in classpath, etc

## package
Just maven install is fine.

## conflict detection
Traverse all jars in a folder, find classes that appear in different jars.
 
you can run with this command.
```shell
java -classpath JvmDemos-0.1.0.jar com.mallow.jvm.tools.ConflictDetector yourpath
```