package dev.jimenez.daos;

import dev.jimenez.entities.Account;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AccountDaoLocal implements AccountDAO {

    private static Map<Integer, Account> accountTable = new HashMap<Integer, Account>();
    private static int idMaker = 0;

    @Override
    public Account createAccount(int clientId,Account account) {
        account.setClientId(clientId);
        account.setAccountId(++idMaker);
        accountTable.put(account.getAccountId(), account);
        return account;

    }

    @Override
    public Set<Account> getAllAccountsByClientId(int clientId) {
        Set<Account> allAccounts = new HashSet<Account>(accountTable.values());
        Set<Account> selectedAccounts = new HashSet<Account>();
        for (Account a : allAccounts) {

            if (a.getAssignedClientId() == clientId) {
                selectedAccounts.add(a);
            }
        }
        return selectedAccounts;
    }

    @Override
    public Account getAccountById(int clientId, int id) {

        Account account1 = new Account();

        Set<Account> allAccounts = new HashSet<Account>(accountTable.values());
        for (Account a : allAccounts) {

            if (a.getAssignedClientId() == clientId && a.getAccountId() == id) {
                account1 = a;
            }
        }

        return account1;
    }

    @Override
    public Account updateAccount(int clientId, Account account) {
        Set<Account> allAccounts = new HashSet<Account>(accountTable.values());

        for (Account a : allAccounts) {

            if (a.getAssignedClientId() == clientId) {
                accountTable.put(account.getAccountId(), account);
            }
        }

        return account;

    }

    @Override
    public boolean deleteAccountById(int clientId, int id) {
        Set<Account> allAccounts = new HashSet<Account>(accountTable.values());

        for (Account a : allAccounts) {

            if (a.getAssignedClientId() == clientId) {
                Account account = accountTable.remove(id);
            }


        }
        return true;


    }
}
