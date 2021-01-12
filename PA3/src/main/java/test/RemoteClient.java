package test;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RemoteClient {

    private static final int PORT = 1337;

    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry("localhost", PORT);
        RemoteInterface service = (RemoteInterface) registry.lookup("//localhost/MyService");
        service.addComputationCount(120);
        System.out.println(service.helloTo("world!"));
    }

}
