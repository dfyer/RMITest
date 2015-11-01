# RMI Example

## Test Environment

OS: Red Hat Enterprise Linux Server release 6.6 (Santiago)
Compiler: javac 1.6.0_36

## How to Run

1. Compile java files
> $ javac *.java

2. Start the Java RMI Registry
> $ rmiregistry (<port>)

3. Start the Server
> java -classpath *classDir* -Djava.rmi.server.codebase=file:*classDir*/ Server

4. Run the Client
> java -classpath *classDir* ReaderClient

## Reference

https://docs.oracle.com/javase/7/docs/technotes/guides/rmi/hello/hello-world.html
