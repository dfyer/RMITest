# RMI Example

## How to Run
Start the Java RMI Registry

> rmiregistry (<port>)

Start the Server

> java -classpath <classDir> -Djava.rmi.server.codebase=file:<classDir>/ Server

Run the Client

> java -classpath <classDir> Client

## Files
### Remote Interface
Reader.java

### Server Implementation
Server.java

### Client Imlementation
Client.java

## Reference

https://docs.oracle.com/javase/7/docs/technotes/guides/rmi/hello/hello-world.html
