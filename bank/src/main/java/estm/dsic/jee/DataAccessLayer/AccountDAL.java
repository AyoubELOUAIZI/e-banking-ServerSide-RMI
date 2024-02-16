package estm.dsic.jee.DataAccessLayer;

import java.rmi.RemoteException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAL  {
  
    
   
    public double checkBalance(int userId) throws RemoteException {
        double balance = 0.0;
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT balance FROM account WHERE user_id = ?")) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                balance = resultSet.getDouble("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return balance;
    }

   
    public boolean deposit(int userId, double amount) throws RemoteException {
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE account SET balance = balance + ? WHERE user_id = ?")) {
            statement.setDouble(1, amount);
            statement.setInt(2, userId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0; // Returns true if at least one row was affected
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

  
    public boolean withdraw(int userId, double amount) throws RemoteException {
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE account SET balance = balance - ? WHERE user_id = ?")) {
            statement.setDouble(1, amount);
            statement.setInt(2, userId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0; // Returns true if at least one row was affected
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

   
    public boolean transfer(int senderUserId, int receiverUserId, double amount) throws RemoteException {
        try (Connection connection = DatabaseUtil.getConnection()) {
            // Start a transaction to ensure atomicity of the transfer
            connection.setAutoCommit(false);
    
            // Deduct amount from sender's account
            boolean senderSuccess = withdraw(senderUserId, amount);
            // If deduction from sender's account fails, rollback the transaction and return false
            if (!senderSuccess) {
                connection.rollback();
                return false;
            }
    
            // Add amount to receiver's account
            boolean receiverSuccess = deposit(receiverUserId, amount);
            // If addition to receiver's account fails, rollback the transaction and return false
            if (!receiverSuccess) {
                connection.rollback();
                return false;
            }
    
            // Commit the transaction if both operations succeed
            connection.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
