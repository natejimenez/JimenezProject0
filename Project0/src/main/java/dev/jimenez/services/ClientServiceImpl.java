package dev.jimenez.services;

import dev.jimenez.daos.ClientDAO;
import dev.jimenez.entities.Client;
import org.apache.log4j.Logger;

import java.util.Set;

public class ClientServiceImpl implements ClientServices{

    private static Logger logger = Logger.getLogger(ClientServiceImpl.class.getName());
    private ClientDAO cdao;

    public ClientServiceImpl(ClientDAO clientDAO){this.cdao = clientDAO;}

    @Override
    public Client createClient(Client client){
        this.cdao.createClient(client);
        return client;
    }

    @Override
    public Set<Client> getAllClients(){
        return this.cdao.getAllClients();
    }

    @Override
    public Client getClientById(int id){

        return this.cdao.getClientById(id);
    }

    @Override
    public Client updateClient(Client client){

        return this.cdao.updateClient(client);
    }

    @Override
    public boolean deleteClientById(int id){

        return this.cdao.deleteClientById(id);
    }
}
