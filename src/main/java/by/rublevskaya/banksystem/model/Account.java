package by.rublevskaya.banksystem.model;

import java.math.BigDecimal;

public class Account {
    private long id;
    private String accountNumber;
    private BigDecimal balance;

    public Account(long id, String accountNumber, BigDecimal balance) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public long getId() {
        return id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
