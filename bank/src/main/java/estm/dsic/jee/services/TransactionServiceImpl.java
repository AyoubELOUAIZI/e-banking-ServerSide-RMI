package estm.dsic.jee.services;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import estm.dsic.jee.DataAccessLayer.TransactionDAL;
import estm.dsic.jee.Models.Transaction;

public class TransactionServiceImpl extends UnicastRemoteObject implements TransactionService {
    private TransactionDAL transactionDAL;
    public TransactionServiceImpl() throws RemoteException {
        super();
        transactionDAL=new TransactionDAL();
    }

    @Override
    public List<Transaction> getTransactionHistory(int userId) throws RemoteException {
        return transactionDAL.getTransactionHistory(userId);
    }

    public boolean registerNewTransaction(int userId, double amount, String transactionType) throws RemoteException {
        return transactionDAL.registerNewTransaction(userId, amount, transactionType);
    }
    
}

