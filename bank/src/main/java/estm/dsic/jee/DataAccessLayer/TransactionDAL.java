package estm.dsic.jee.DataAccessLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import estm.dsic.jee.Models.Transaction;

public class TransactionDAL {
    public List<Transaction> getTransactionHistory(int userId) {
        List<Transaction> transactionHistory = new ArrayList<>();
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM historic WHERE account_id IN (SELECT account_id FROM Account WHERE user_id = ?)")) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Transaction transaction = new Transaction();
                transaction.setTransactionId(resultSet.getInt("transaction_id"));
                transaction.setAccountId(resultSet.getInt("account_id"));
                transaction.setTransactionType(resultSet.getString("transaction_type"));
                transaction.setAmount(resultSet.getDouble("amount"));
                transaction.setTransactionDate(resultSet.getTimestamp("transaction_date").toLocalDateTime());
                transactionHistory.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception or log error
        }
        System.out.println("\nTransaction history called successfully by the user with id✅: "+userId);
        return transactionHistory;
    }

    public boolean registerNewTransaction(int userId, double amount, String transactionType) {
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO historic (account_id, transaction_type, amount) " +
                     "VALUES ((SELECT account_id FROM account WHERE user_id = ?), ?, ?)")) {
            statement.setInt(1, userId);
            statement.setString(2, transactionType);
            statement.setDouble(3, amount);
            int rowsAffected = statement.executeUpdate();
            System.out.println("---> New transaction registered🚀🚀:\n{"+"UserId : "+ userId+"\nAmount : "+ amount+"\nTransaction Type : "+ transactionType+"}\n");
            return rowsAffected > 0; // Returns true if at least one row was affected
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
