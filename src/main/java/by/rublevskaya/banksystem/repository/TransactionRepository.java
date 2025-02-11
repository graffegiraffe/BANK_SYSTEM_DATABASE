package by.rublevskaya.banksystem.repository;

import by.rublevskaya.banksystem.config.DatabaseConfig;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionRepository {
    private Connection getConnection() throws SQLException {
        return DatabaseConfig.getConnection();
    }

    public void logTransaction(String fromAccount, String toAccount, BigDecimal amount) throws SQLException {
        String query = "INSERT INTO transactions (from_account, to_account, amount, timestamp) VALUES (?, ?, ?, NOW())";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, fromAccount);
            statement.setString(2, toAccount);
            statement.setBigDecimal(3, amount);
            statement.executeUpdate();
        }
    }
}