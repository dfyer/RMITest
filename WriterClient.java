
import java.io.*;
import java.util.Random;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class WriterClient {
    private WriterClient() {}

    public static void main(String[] args) {
        // File location and port number with argument
        String traceLocation = (args.length < 1) ? "seq_trace.log" : args[0];
        String host = (args.length < 2) ? null : args[1];

        // Announcing the location of trace file and host address
        System.out.println("Trace File: " + traceLocation);
        System.out.println("Host Address: " + host);

        try {
            //////////////// CHECK CONNECTION BEGIN ////////////////
            // Get registry for the host
            Registry registry = LocateRegistry.getRegistry(host);

            // Bind the remote interface
            TestInterface stub = (TestInterface) registry.lookup("TestInterface");

            // Check the Connection Manually
            //   Call the remote method and retrieve the response
            String response = stub.checkConnection();
            System.out.println("response: " + response);
            //////////////// CHECK CONNECTION END ////////////////

            //////////////// WRITE TEST BEGIN ////////////////
            // WRITE TEST: read the trace file line by line
            BufferedReader trace = new BufferedReader(new FileReader(traceLocation));
            String line;

            // XXX: Assuming datasize is always 1024
            // XXX: Always creatnig data with fixed key
            // WRITE TEST: dummy string for write
            String randCharSet = "AEIOUaeiouVSTRBGDvstrbgc123789;][=+,.*!$";
            String dummydata = "FFFF";
            Random r = new Random();
            for (int i = 0; i < 1024; i++) {
                dummydata += randCharSet.charAt(r.nextInt(randCharSet.length()));
            }

            // WRITE TEST: count for result summary
            int count = 0;
            long start = System.currentTimeMillis();
            while ((line = trace.readLine()) != null) {
                // WRITE TEST: parsing the trace file line
                int key = Integer.parseInt(line.split("\t")[0]);
                int datasize = Integer.parseInt(line.split("\t")[1]);

                // WRITE TEST: if key is found, response is
                //            <datasize>-sized byte array that are written ( = dummy data )
                response = stub.writeRecord(key, datasize, dummydata);

                // NOTE: checking the Result is commented due to print cost
                if (response != null) {
                    count++;
                    //System.out.println("response for key " + key + ": " + response);
                } else {
                    //System.out.println("not found");
                }
            }
            // READ TEST: result summary
            System.out.println(count + " keys found in remote server in " + (System.currentTimeMillis() - start) + " ms");
            trace.close();
            //////////////// READ TEST END ////////////////
        } catch (Exception e) {
            // Exception Handling
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}

