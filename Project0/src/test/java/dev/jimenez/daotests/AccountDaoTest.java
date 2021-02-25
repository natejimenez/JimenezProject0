package dev.jimenez.daotests;

import dev.jimenez.daos.AccountDAO;
import dev.jimenez.daos.AccountDaoLocal;
import dev.jimenez.daos.AccountDaoPostgres;
import dev.jimenez.entities.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class AccountDaoTest {

    private static AccountDAO adao = new AccountDaoPostgres();
    private static Account testAccount = null;

    @Test
    @Order(1)
    void create_account(){

        Account account1 = new Account(1,"Checking",0,100);

        adao.createAccount(account1.getAssignedClientId(), account1);
        System.out.println(account1);
        testAccount = account1;
        Assertions.assertNotEquals(0,account1.getAccountId());
    }

    @Test
    @Order(2)
    void get_all_accounts_by_client_id(){
        Account account1 = new Account(1,"Checking",0,100);
        Account account2 = new Account(1,"Checking",0,1000);
        Account account3 = new Account(1,"Checking",0,10000);

        adao.createAccount(account1.getAssignedClientId(), account1);
        adao.createAccount(account2.getAssignedClientId(), account2);
        adao.createAccount(account3.getAssignedClientId(), account3);

        Set<Account> allClientAccounts = adao.getAllAccountsByClientId(1);
        Assertions.assertTrue(allClientAccounts.size()>2);

    }

    @Test
    @Order(3)
    void get_one_account_by_client_id_account_id(){

        Account account1 = new Account(1,"Checking",0,100);
        Account account2 = new Account(1,"Checking",0,1000);
        Account account3 = new Account(1,"Checking",0,10000);

        adao.createAccount(account1.getAssignedClientId(), account1);
        adao.createAccount(account2.getAssignedClientId(), account2);
        adao.createAccount(account3.getAssignedClientId(), account3);
        testAccount = account1;

        int id = testAccount.getAccountId();
        int clientId = testAccount.getAssignedClientId();
        Account foundAccount= adao.getAccountById(clientId,id);

        Assertions.assertEquals(testAccount.getAccountType(),foundAccount.getAccountType());

    }

    @Test
    @Order(4)
    void update_account(){
        Account account1 = new Account(1,"Checking",0,100);
        Account account2 = new Account(1,"Checking",0,1000);
        Account account3 = new Account(1,"Checking",0,10000);

        adao.createAccount(account1.getAssignedClientId(), account1);
        adao.createAccount(account2.getAssignedClientId(), account2);
        adao.createAccount(account3.getAssignedClientId(), account3);
        testAccount = account1;

        Account account = adao.getAccountById(testAccount.getAssignedClientId(), testAccount.getAccountId());
        account.setAccountType("Savings");
        adao.updateAccount(testAccount.getAssignedClientId(), account);

        Account updatedAccount = adao.getAccountById(testAccount.getAssignedClientId(), testAccount.getAccountId());
        Assertions.assertEquals("Savings",updatedAccount.getAccountType());

    }

    @Test
    @Order(5)
    void delete_account_by_id(){
        Account account1 = new Account(1,"Checking",0,100);

        testAccount = account1;
        int clientId = testAccount.getAssignedClientId();
        int id = testAccount.getAccountId();
        boolean result = adao.deleteAccountById(clientId,id);
        Assertions.assertTrue(result);
    }




}
