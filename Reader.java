
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Reader extends Remote {
    public String readRecords() throws RemoteException;
}
