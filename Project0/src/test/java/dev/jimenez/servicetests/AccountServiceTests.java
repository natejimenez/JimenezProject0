package dev.jimenez.servicetests;


import dev.jimenez.daos.AccountDAO;
import dev.jimenez.entities.Account;
import dev.jimenez.services.AccountServiceImpl;
import dev.jimenez.services.AccountServices;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTests {

    @Mock
    AccountDAO accountDAO = null;


    private AccountServices accountServices = null;

    @BeforeEach
    void setUp(){

        Account account1 = new Account(1,"Checking",1,500);
        Account account2 = new Account(2,"Savings",2,1000);
        Account account3 = new Account(1,"Mutual Fund",3,2000);
        Account account4 = new Account(4,"Vacation",4,100);
        Account account5 = new Account(1,"IRA",5,100);
        Account account6 = new Account(3,"Roth IRA",6,10000);

        Set<Account> accountSet = new HashSet<Account>();
        accountSet.add(account1);
        accountSet.add(account2);
        accountSet.add(account3);
        accountSet.add(account4);
        accountSet.add(account5);
        accountSet.add(account6);

        int id = 1;
        Mockito.when(accountDAO.getAllAccountsByClientId(id)).thenReturn(accountSet);

        this.accountServices = new AccountServiceImpl(this.accountDAO);
    }

    @Test
    void search_by_balance(){
        Set<Account> accounts = this.accountServices.getAccountsByBalance(1,400,2000);
        System.out.println(accounts);

        Assertions.assertEquals(3,accounts.size());
    }


}
