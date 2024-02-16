package estm.dsic.jee.services;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import estm.dsic.jee.DataAccessLayer.UserDAL;
import estm.dsic.jee.Models.User;

public class AuthenticationServiceImpl extends UnicastRemoteObject implements AuthenticationService {
    private UserDAL userDAL; // Injected UserDAL instance

    public AuthenticationServiceImpl() throws RemoteException {
        super();
        this.userDAL = new UserDAL(); // Automatically create and inject UserDAL instance
    }

    @Override
    public User authenticateUser(String username, String password) throws RemoteException {
        return userDAL.authenticateUser(username, password);
    }
}
