package dev.jimenez.controllers;

import com.google.gson.Gson;
import dev.jimenez.daos.ClientDaoLocal;
import dev.jimenez.daos.ClientDaoPostgres;
import dev.jimenez.entities.Client;
import dev.jimenez.services.ClientServiceImpl;
import dev.jimenez.services.ClientServices;
import io.javalin.http.Handler;
import org.apache.log4j.Logger;

import java.util.Set;

public class ClientController {

    private ClientServices clientServices = new ClientServiceImpl(new ClientDaoPostgres());
    private static Logger logger = Logger.getLogger(ClientServiceImpl.class.getName());

    public Handler getAllClientsHandler = ctx -> {

        Set<Client> allClients = this.clientServices.getAllClients();
        Gson gson = new Gson();
        String clientsJSON = gson.toJson(allClients);

        ctx.result(clientsJSON);
        ctx.status(200);
        logger.info("All clients were retrieved");
    };

    public Handler getClientByIdHandler = ctx -> {
        int id = Integer.parseInt((ctx.pathParam("cid")));
        Client client = this.clientServices.getClientById(id);

        if(client == null){
            ctx.result("Client not found");
            ctx.status(404);
            logger.error("404: Client was searched for but not found");

        }else{
            Gson gson = new Gson();
            String bookJson = gson.toJson(client);
            ctx.result(bookJson);
            ctx.status(200);
            logger.info("The client with the ID of "+id+" was retrieved.");


        }
    };

    public Handler createClientHandler = ctx -> {
        String body = ctx.body();
        Gson gson = new Gson();

        Client client = gson.fromJson(body,Client.class);

        this.clientServices.createClient(client);
        String json = gson.toJson(client);
        ctx.result(json);
        ctx.status(201);
        logger.info("A client was created for "+client.getFirstName()+" "+client.getLastName());

    };

    public Handler updateClientHandler = ctx -> {

        int id = Integer.parseInt(ctx.pathParam("cid"));
        Client client = this.clientServices.getClientById(id);

        if(client == null){
            ctx.result("Client not found");
            ctx.status(404);
            logger.error("404: Client was searched for but not found");

        }else {
            String body = ctx.body();

            Gson gson = new Gson();
            client= gson.fromJson(body,Client.class);

            client.setClientID(id);
            this.clientServices.updateClient(client);
            String json = gson.toJson(client);
            ctx.result(json);
            ctx.status(200);
            logger.info("The client "+client.getFirstName()+" "+client.getLastName()+" was updated");

        }
    };

    public Handler deleteClientHandler = ctx -> {
        int id = Integer.parseInt(ctx.pathParam("cid"));
        Client client = this.clientServices.getClientById(id);

        if(client == null){
            ctx.result("Client not found, unable to delete");
            ctx.status(404);
            logger.error("404: Client was searched for but not found");

        }else {
            boolean result = this.clientServices.deleteClientById(id);
            if (result ==true) {
                ctx.result("Client was successfully deleted.");
                ctx.status(200);
                logger.info("The client with ID of "+id+" was deleted.");

            }else{
                ctx.result("Unable to delete client successfully, there are existing accounts for this client.");

                logger.error("Unable to delete client");


            }
            }
        };

       }


