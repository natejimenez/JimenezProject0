package dev.jimenez.services;

import dev.jimenez.daos.AccountDAO;
import dev.jimenez.entities.Account;
import org.apache.log4j.Logger;

import java.util.HashSet;
import java.util.Set;

public class AccountServiceImpl implements AccountServices{


    private AccountDAO adao;
    public AccountServiceImpl(AccountDAO accountDAO){this.adao=accountDAO;}

    @Override
    public Account createAccount(int clientId,Account account){

        this.adao.createAccount(clientId,account);
        return account;
    };

    @Override
    public Set<Account> getAllAccountsByClientId(int assignedClientId){

        return this.adao.getAllAccountsByClientId(assignedClientId);
    };

    @Override
    public Account getAccountById(int assignedClientId,int id){

        return this.adao.getAccountById(assignedClientId,id);
    };

    @Override
    public Set<Account> getAccountsByBalance(int assignedClientId,int bal1,int bal2){

        Set<Account> allAccounts = this.getAllAccountsByClientId(assignedClientId);
        Set<Account> selectedAccounts = new HashSet<Account>();

        for(Account a : allAccounts){

            if(bal1 <= a.getBalance() && a.getBalance() <= bal2){
                selectedAccounts.add(a);

            }
        }
        return selectedAccounts;
    };

    @Override
    public Account updateAccountById(int assignedClientId,Account account){

        return this.adao.updateAccount(assignedClientId,account);
    };

    @Override
    public boolean deleteAccountById(int assignedClientId,int id){

        return this.adao.deleteAccountById(assignedClientId,id);
    };

}
