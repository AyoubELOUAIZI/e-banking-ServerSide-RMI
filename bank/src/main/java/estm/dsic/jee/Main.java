package estm.dsic.jee;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import estm.dsic.jee.services.AccountServiceImpl;
import estm.dsic.jee.services.AuthenticationServiceImpl;


public class Main {
    public static void main(String[] args) {
        try {
            AuthenticationServiceImpl authenticationService = new AuthenticationServiceImpl();
            AccountServiceImpl accountService = new AccountServiceImpl();
            
            Registry registry = LocateRegistry.createRegistry(52369);
            
            Naming.rebind("rmi://" + InetAddress.getLocalHost().getHostAddress() + ":52369/AuthenticationService", authenticationService);
            Naming.rebind("rmi://" + InetAddress.getLocalHost().getHostAddress() + ":52369/AccountService", accountService);
            
            System.out.println("Server is running and waiting for client calls...");
        } catch (RemoteException | UnknownHostException | MalformedURLException e) {
            e.printStackTrace();
            System.out.println("Error while setting up RMI server");
        }
    }
}

