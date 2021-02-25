package dev.jimenez.controllers;

import com.google.gson.Gson;
import dev.jimenez.daos.AccountDaoLocal;
import dev.jimenez.daos.AccountDaoPostgres;
import dev.jimenez.daos.ClientDaoPostgres;
import dev.jimenez.entities.Account;
import dev.jimenez.entities.Client;
import dev.jimenez.services.AccountServiceImpl;
import dev.jimenez.services.AccountServices;
import dev.jimenez.services.ClientServiceImpl;
import dev.jimenez.services.ClientServices;
import io.javalin.http.Handler;
import org.apache.log4j.Logger;

import java.util.Set;

public class AccountController {

    private AccountServices accountServices = new AccountServiceImpl(new AccountDaoPostgres());

    private ClientServices clientServices = new ClientServiceImpl(new ClientDaoPostgres());
    private static Logger logger = Logger.getLogger(AccountServiceImpl.class.getName());

    public Handler getAllAccountsByClientHandler = ctx -> {

        String balanceHigher = ctx.queryParam("amountLessThan", "NONE");
        String balanceLower = ctx.queryParam("amountGreaterThan", "NONE");

        int id = Integer.parseInt((ctx.pathParam("cid")));
        Set<Account> allAccountsByClient = this.accountServices.getAllAccountsByClientId(id);
        Client client = this.clientServices.getClientById(id);
        if (balanceLower.equals("NONE") && balanceHigher.equals("NONE")) {

            if (client == null) {
                ctx.result("Client not found");
                ctx.status(404);
            } else {
                Gson gson = new Gson();
                String accountsJSON = gson.toJson(allAccountsByClient);
                ctx.result(accountsJSON);
                ctx.status(200);
                logger.info("All accounts for client with ID "+id+" were retrieved.");

            }
        } else {
            int higherBalance = Integer.parseInt(ctx.queryParam("amountLessThan"));
            int lowerBalance = Integer.parseInt(ctx.queryParam("amountGreaterThan"));
            Set<Account> accounts = this.accountServices.getAccountsByBalance(id, lowerBalance, higherBalance);
            if (client == null) {
                ctx.result("Client not found");
                ctx.status(404);
            } else {
                Gson gson = new Gson();
                String selectedAccountsJSON = gson.toJson(accounts);
                ctx.result(selectedAccountsJSON);
                ctx.status(200);
                logger.info("Only accounts between $"+lowerBalance+" and $"+higherBalance+" were retrieved for client ID of "+id);


            }


        }

    };

    public Handler getAccountByIdHandler = ctx -> {

        int clientId = Integer.parseInt(ctx.pathParam("cid"));
        int id = Integer.parseInt(ctx.pathParam("aid"));
        Account account = this.accountServices.getAccountById(clientId,id);

        if(account == null){
            ctx.result("Account not found");
            ctx.status(404);
        }else{
            Gson gson = new Gson();
            String accountJson = gson.toJson(account);
            ctx.result(accountJson);
            ctx.status(200);
            logger.info("Account with client ID "+clientId+" and account ID"+id+" was retrieved.");

        }

    };



    public Handler createAccountHandler = ctx -> {

        int clientId = Integer.parseInt(ctx.pathParam("cid"));
        String body = ctx.body();
        Gson gson = new Gson();

        Account account = gson.fromJson(body,Account.class);

        this.accountServices.createAccount(clientId,account);
        String json = gson.toJson(account);
        ctx.result(json);
        ctx.status(201);
        logger.info("A new "+account.getAccountType()+" account was created for the client with ID of "+clientId+".");

    };

    public Handler updateAccountHandler = ctx -> {

        int clientId = Integer.parseInt(ctx.pathParam("cid"));
        int id = Integer.parseInt(ctx.pathParam("aid"));

        Account account = this.accountServices.getAccountById(clientId,id);



        if (account == null){
            ctx.result("Account not found");
            ctx.status(404);

        }else{
            String body = ctx.body();
            Gson gson = new Gson();
            Account account1 = gson.fromJson(body,Account.class);
            account1.setAccountId(id);
            this.accountServices.updateAccountById(clientId,account1);
            String json = gson.toJson(account1);
            ctx.result(json);
            ctx.status(200);
            logger.info("An update was made to account "+account1.getAccountId()+" for client "+clientId);

        }
    };


    public Handler deleteAccountHandler = ctx -> {
        int clientId = Integer.parseInt(ctx.pathParam("cid"));
        int id = Integer.parseInt(ctx.pathParam("aid"));

        Account account = this.accountServices.getAccountById(clientId,id);

        if(account == null){
            ctx.result("Account not found, unable to delete");
            ctx.status(404);
        }else {
            boolean result = this.accountServices.deleteAccountById(clientId,id);
            if (result ==true) {
                ctx.result("Client was successfully deleted.");
                ctx.status(200);
            }else{
                ctx.result("Unable to delete client successfully.");

            }
        }

    };


}
