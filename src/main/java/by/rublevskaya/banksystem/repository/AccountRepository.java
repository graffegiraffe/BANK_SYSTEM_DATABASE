package by.rublevskaya.banksystem.repository;

import by.rublevskaya.banksystem.config.DatabaseConfig;
import by.rublevskaya.banksystem.model.Account;

import java.math.BigDecimal;
import java.sql.*;

public class AccountRepository {
    private Connection getConnection() throws SQLException {
        return DatabaseConfig.getConnection();
    }

    public void createAccount(String accountNumber, BigDecimal initialBalance) throws SQLException {
        String query = "INSERT INTO accounts (account_number, balance) VALUES (?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, accountNumber);
            statement.setBigDecimal(2, initialBalance);
            statement.executeUpdate();
        }
    }

    public Account getAccountByNumber(String accountNumber) throws SQLException {
        String query = "SELECT * FROM accounts WHERE account_number = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, accountNumber);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Account(
                        resultSet.getLong("id"),
                        resultSet.getString("account_number"),
                        resultSet.getBigDecimal("balance")
                );
            }
        }
        return null;
    }

    public void updateBalance(String accountNumber, BigDecimal newBalance) throws SQLException {
        String query = "UPDATE accounts SET balance = ? WHERE account_number = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setBigDecimal(1, newBalance);
            statement.setString(2, accountNumber);
            statement.executeUpdate();
        }
    }
}