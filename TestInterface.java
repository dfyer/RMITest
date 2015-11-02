
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TestInterface extends Remote {
    public void addClient() throws RemoteException;
    public int countClient() throws RemoteException;
    public String checkConnection() throws RemoteException;
    public String readRecord(int key, int datasize) throws RemoteException;
    public boolean writeRecord(int key, int datasize, String data) throws RemoteException;
}
