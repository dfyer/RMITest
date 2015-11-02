
import java.io.*;
import java.util.Arrays;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Server implements TestInterface {
    private String dbLocation;
    private int clients;

    public Server() throws RemoteException {
        clients = 0;
        dbLocation = "db_100k.dat";
    }

    public void addClient() throws RemoteException {
        clients++;
    }

    public int countClient() {
        return clients;
    }

    public String checkConnection() {
        // Testing Connection
        return "Hello, World!";
    }

    public String readRecord(int key, int datasize) {
        try {
            // Read db file. to identify the key
            RandomAccessFile rf = new RandomAccessFile(dbLocation, "rws");
            byte[] data = new byte[datasize];
            rf.seek((4 + datasize) * key + 4);
            rf.read(data, 0, datasize);
            rf.close();
            return new String(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean writeRecord(int key, int datasize, String data) {
        try {
            // Open DB file
            RandomAccessFile rf = new RandomAccessFile(dbLocation, "rws");
            // XXX: Assuming always key == line number
            rf.seek((4 + datasize) * key + 4);
            rf.write(data.getBytes(), 0, datasize);
            rf.close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String args[]) {

        try {
            Server obj = new Server();
            TestInterface stub = (TestInterface) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("TestInterface", stub);

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
        }
    }
}
