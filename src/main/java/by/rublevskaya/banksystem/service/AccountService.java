package by.rublevskaya.banksystem.service;

import by.rublevskaya.banksystem.config.DatabaseConfig;
import by.rublevskaya.banksystem.model.Account;
import by.rublevskaya.banksystem.repository.AccountRepository;
import by.rublevskaya.banksystem.repository.TransactionRepository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

public class AccountService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public void transferFunds(String fromAccount, String toAccount, BigDecimal amount) throws SQLException {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("The transfer amount must be greater than zero");
        }

        try (Connection connection = DatabaseConfig.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Account sender = accountRepository.getAccountByNumber(fromAccount);
                Account receiver = accountRepository.getAccountByNumber(toAccount);

                if (sender == null) {
                    throw new IllegalArgumentException("Sender account does not exist: " + fromAccount);
                }
                if (receiver == null) {
                    throw new IllegalArgumentException("Recipient account does not exist: " + toAccount);
                }
                if (sender.getBalance().compareTo(amount) < 0) {
                    throw new IllegalArgumentException("Not enough funds in account: " + fromAccount);
                }
                accountRepository.updateBalance(fromAccount, sender.getBalance().subtract(amount));
                accountRepository.updateBalance(toAccount, receiver.getBalance().add(amount));

                transactionRepository.logTransaction(fromAccount, toAccount, amount);
                connection.commit();
            } catch (Exception e) {
                connection.rollback();
                throw e;
            }
        }
    }
}
