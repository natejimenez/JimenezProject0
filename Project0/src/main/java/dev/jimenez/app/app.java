package dev.jimenez.app;

import dev.jimenez.controllers.AccountController;
import dev.jimenez.controllers.ClientController;
import io.javalin.Javalin;

public class app {

    public static void main(String[] args) {

        Javalin app = Javalin.create();

        ClientController clientController = new ClientController();
        AccountController accountController = new AccountController();

        // POST /clients => Creates a new client

        app.post("/clients",clientController.createClientHandler);

        // GET /clients => gets all clients

        app.get("/clients",clientController.getAllClientsHandler);

        // GET /clients/10 => get client with id of 10

        app.get("/clients/:cid",clientController.getClientByIdHandler);

        // PUT /clients/12 => updates client with id of 12

        app.put("clients/:cid",clientController.updateClientHandler);

        // DELETE /clients/15 => deletes client with the id of15

        app.delete("/clients/:cid",clientController.deleteClientHandler);

        // POST /clients/5/accounts =>creates a new account for client with the id of 5

        app.post("/clients/:cid/accounts", accountController.createAccountHandler);

        // GET /clients/7/accounts => get all accounts for client 7

        app.get("/clients/:cid/accounts",accountController.getAllAccountsByClientHandler);

        // GET /clients/9/accounts/4 => get account 4 for client 9

        app.get("/clients/:cid/accounts/:aid", accountController.getAccountByIdHandler);

        // PUT /clients/10/accounts/3 => update account  with the id 3 for client 10

        app.put("/clients/:cid/accounts/:aid",accountController.updateAccountHandler);

        // DELETE /clients/15/accounts/6 => delete account 6 for client 15

        app.delete("/clients/:cid/accounts/:aid",accountController.deleteAccountHandler);

        app.start();
        // starts web server

    }
}
