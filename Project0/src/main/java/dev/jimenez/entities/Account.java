package dev.jimenez.entities;

public class Account {


    private int assignedClientId;
    private String accountType;
    private int accountId;
    private double balance;

    public Account(){}

    public Account(int assignedClientId, String accountType, int accountId, double balance) {
        this.assignedClientId = assignedClientId;
        this.accountType = accountType;
        this.accountId = accountId;
        this.balance = balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }



    public int getAssignedClientId() {
        return assignedClientId;
    }

    public void setClientId(int assignedClientId) {
        this.assignedClientId = assignedClientId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "clientId=" + assignedClientId +
                ", accountId=" + accountId +
                ", balance=" + balance +
                '}';
    }
}
