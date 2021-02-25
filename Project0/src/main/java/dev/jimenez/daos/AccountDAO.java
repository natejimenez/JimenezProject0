package dev.jimenez.daos;

import dev.jimenez.entities.Account;

import java.util.HashMap;
import java.util.Set;

public interface AccountDAO {

    Account createAccount(int assignedClientId,Account account);

    Set<Account> getAllAccountsByClientId(int id);
    Account getAccountById(int assignedClientId,int id);

    Account updateAccount(int assignedClientId, Account account);
    boolean deleteAccountById(int assignedClientId,int id);

}
