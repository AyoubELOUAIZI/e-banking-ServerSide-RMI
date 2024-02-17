package estm.dsic.jee.services;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import estm.dsic.jee.DataAccessLayer.AccountDAL;

public class AccountServiceImpl extends UnicastRemoteObject implements AccountService {
    private AccountDAL accountDAL;
    private UserServiceImpl userServiceImpl = new UserServiceImpl();
    private TransactionServiceImpl transactionService = new TransactionServiceImpl();

    public AccountServiceImpl() throws RemoteException {
        super();
        this.accountDAL = new AccountDAL();
    }

    @Override
    public double checkBalance(int userId) throws RemoteException {
        return accountDAL.checkBalance(userId);
    }

    // @Override
    // public boolean deposit(int userId, double amount) throws RemoteException {
    // return accountDAL.deposit(userId, amount);
    // }

    // @Override
    // public boolean withdraw(int userId, double amount) throws RemoteException {
    // return accountDAL.withdraw(userId, amount);
    // }

    @Override
    public boolean deposit(int userId, double amount) throws RemoteException {
        boolean depositSuccess = accountDAL.deposit(userId, amount);
        if (depositSuccess) {
            transactionService.registerNewTransaction(userId, amount, "deposit");
        }
        return depositSuccess;
    }

    @Override
    public boolean withdraw(int userId, double amount) throws RemoteException {
        boolean withdrawSuccess = accountDAL.withdraw(userId, amount);
        if (withdrawSuccess) {
            transactionService.registerNewTransaction(userId, amount, "withdraw");
        }
        return withdrawSuccess;
    }

    @Override
    public boolean transfer(int senderUserId, String receiverUsername, double amount) throws RemoteException {

        System.out.println("the transfer is started just right now...");

        // Step 1: Retrieve the receiver's user ID using their username
        int receiverUserId = userServiceImpl.getUserIdByUsername(receiverUsername);
        if (receiverUserId == -1) {
            // Receiver does not exist
            return false;
        }

        // Step 2: Verify that the amount is less than or equal to the sender's balance
        double senderBalance = checkBalance(senderUserId);
        if (amount > senderBalance) {
            // Insufficient balance in the sender's account
            return false;
        }

        // Step 3: Update balances for both sender and receiver
        boolean senderSuccess = withdrawByTransfer(senderUserId, amount);
        boolean receiverSuccess = depositByTransfer(receiverUserId, amount);

        // Return true if both operations succeed, false otherwise
        return senderSuccess && receiverSuccess;
    }


    private boolean depositByTransfer(int userId, double amount) throws RemoteException {
        boolean depositSuccess = accountDAL.deposit(userId, amount);
        if (depositSuccess) {
            transactionService.registerNewTransaction(userId, amount, "transfer");
        }
        return depositSuccess;
    }

    
    private boolean withdrawByTransfer(int userId, double amount) throws RemoteException {
        boolean withdrawSuccess = accountDAL.withdraw(userId, amount);
        if (withdrawSuccess) {
            transactionService.registerNewTransaction(userId, (-1)*amount, "transfer");
        }
        return withdrawSuccess;
    }

}
