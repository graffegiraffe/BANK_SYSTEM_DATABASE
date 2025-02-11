package by.rublevskaya.banksystem.app;

import by.rublevskaya.banksystem.repository.AccountRepository;
import by.rublevskaya.banksystem.repository.TransactionRepository;
import by.rublevskaya.banksystem.service.AccountService;

import java.math.BigDecimal;
import java.sql.SQLException;

public class Application {
    public static void main(String[] args) {
        AccountRepository accountRepository = new AccountRepository();
        TransactionRepository transactionRepository = new TransactionRepository();
        AccountService accountService = new AccountService(accountRepository, transactionRepository);

        try {
            accountRepository.createAccount("25112005", new BigDecimal("1000"));
            accountRepository.createAccount("50021152", new BigDecimal("500"));

            System.out.println("Баланс 25112005: " + accountRepository.getAccountByNumber("25112005").getBalance());
            System.out.println("Баланс 50021152: " + accountRepository.getAccountByNumber("50021152").getBalance());

            accountService.transferFunds("25112005", "50021152", new BigDecimal("200"));

            System.out.println("Баланс 25112005: " + accountRepository.getAccountByNumber("25112005").getBalance());
            System.out.println("Баланс 50021152: " + accountRepository.getAccountByNumber("50021152").getBalance());
        }catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unknown error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

