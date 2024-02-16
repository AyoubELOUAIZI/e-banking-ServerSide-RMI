package estm.dsic.jee.services;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import estm.dsic.jee.DataAccessLayer.UserDAL;

public class UserServiceImpl extends UnicastRemoteObject implements UserService {
    private UserDAL userDAL;
    public UserServiceImpl() throws RemoteException {
        super();
        this.userDAL = new UserDAL();
    }

    @Override
    public boolean createUser(String username, String password) throws RemoteException {
        // Your implementation code to create a new user in the database
        return true; // or false based on success of user creation
    }

    @Override
    public boolean updateUser(String username, String newPassword) throws RemoteException {
        // Your implementation code to update user password in the database
        return true; // or false based on success of user update
    }

    @Override
    public boolean deleteUser(String username) throws RemoteException {
        // Your implementation code to delete user from the database
        return true; // or false based on success of user deletion
    }

    @Override
    public int getUserIdByUsername(String username) throws RemoteException {
       return userDAL.getUserIdByUsername(username);
    }
}
