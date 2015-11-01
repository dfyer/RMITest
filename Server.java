
import java.io.*;
import java.util.Arrays;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Server implements TestInterface {
    static private String dbLocation;
    public Server() {}

    public String checkConnection() {
        // Testing Connection
        return "Hello, World!";
    }
    
    public String readRecord(int key, int datasize) {
        try {
            // Read db file. to identify the key
            DataInputStream db = new DataInputStream(new FileInputStream(dbLocation));
            byte[] line = new byte[4 + datasize];
            int len; // # of bytes read
            int counter = 0;

            // Scan binary file sequentially
            while ((len = db.read(line)) > 0) {
                // Parsing
                ByteBuffer bb = ByteBuffer.wrap(Arrays.copyOfRange(line, 0, 4));
                bb.order(ByteOrder.LITTLE_ENDIAN);
                int candidate_key = bb.getInt();
                // Compare and decide wheter key's found or not
                if (key == candidate_key) {
                    // Convert the byte array to String for print and return type matching
                    //   - Print functions are commented to reduce excessive cost
                    //System.out.println("Found the key: " + candidate_key);
                    String data = Arrays.toString(Arrays.copyOfRange(line, 4, 4 + datasize));
                    //System.out.println("Data: " + data);
                    db.close();
                    return data;
                }
            }
            //System.err.println(key + " NOT FOUND");
            db.close();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String writeRecord(int key, int datasize, String data) {
        try {
            // Open DB file with APPEND mode
            File f = new File(dbLocation);
            BufferedWriter bw = new BufferedWriter(new FileWriter(f.getName(), true));
            bw.write(data);
            bw.close();
            
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String args[]) {
        dbLocation = (args.length < 1) ? "db_100k.dat" : args[0];

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
