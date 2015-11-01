
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TestInterface extends Remote {
    public String checkConnection() throws RemoteException;
    public String readRecord(int key, int datasize) throws RemoteException;
    public String writeRecord(int key, int datasize, String data) throws RemoteException;
}
