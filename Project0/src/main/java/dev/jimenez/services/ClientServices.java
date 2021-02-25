package dev.jimenez.services;


import dev.jimenez.entities.Client;

import java.util.Set;

public interface ClientServices {

    Client createClient(Client client);
    Set<Client> getAllClients();
    Client getClientById(int id);
    Client updateClient(Client client);
    boolean deleteClientById(int id);

}
