package dev.jimenez.daos;

import dev.jimenez.entities.Client;

import java.util.Set;

public interface ClientDAO {

    Client createClient(Client client);

    Set<Client> getAllClients();
    Client getClientById(int id);
    Client updateClient(Client client);
    boolean deleteClientById(int id);

}
