package RMI;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RemoteService extends UnicastRemoteObject implements RemoteInterface {

    private static final int PORT = 1337;
    private static final long serialVersionUID = 1L;
    private static long computationInMilliseconds= 0L;

    private RemoteService() throws RemoteException {
        super();
    }

    public String helloTo(String name) throws RemoteException {
        System.out.println("Connection made...");
        return "Hello, " + name;
    }

    public void addComputationCount(long computationTime){
        computationInMilliseconds += computationTime;
        System.out.println(computationInMilliseconds);

    }

    public static void main(String[] args) throws RemoteException {
        Registry registry = LocateRegistry.createRegistry(PORT);
        registry.rebind("//localhost/MyService", new RemoteService());
    }

}
