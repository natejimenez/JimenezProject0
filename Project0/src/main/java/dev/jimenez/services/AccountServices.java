package dev.jimenez.services;

import dev.jimenez.entities.Account;

import java.util.HashMap;
import java.util.Set;

public interface AccountServices {

    Account createAccount(int assignedClientId,Account account);

    Set<Account> getAllAccountsByClientId(int assignedClientId);
    Account getAccountById(int assignedClientId,int id);
    public Set<Account> getAccountsByBalance(int assignedClientId,int bal1,int bal2);
    Account updateAccountById(int assignedClientId,Account account);
    boolean deleteAccountById(int assignedClientId,int id);
}
