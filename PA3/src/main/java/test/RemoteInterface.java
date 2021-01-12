package test;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteInterface extends Remote {

    String helloTo(String name) throws RemoteException;
    void addComputationCount(long computationTime) throws RemoteException;

}
