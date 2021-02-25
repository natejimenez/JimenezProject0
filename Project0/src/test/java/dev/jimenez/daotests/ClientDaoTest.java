package dev.jimenez.daotests;

import dev.jimenez.daos.ClientDAO;
import dev.jimenez.daos.ClientDaoLocal;
import dev.jimenez.daos.ClientDaoPostgres;
import dev.jimenez.entities.Client;
import org.junit.jupiter.api.*;

import java.util.Set;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClientDaoTest {

    private static ClientDAO cdao = new ClientDaoPostgres();
    private static Client testClient = null;

    @Test
    @Order(1)
    void create_client(){

        Client jpMorgan = new Client(0,"Nathan","Jimenez");
        cdao.createClient(jpMorgan);
        System.out.println(jpMorgan);
        testClient = jpMorgan;
        Assertions.assertNotEquals(0,jpMorgan.getClientID());
    }

    @Test
    @Order(2)
    void get_client_by_id(){
        int id = testClient.getClientID();
        Client client = cdao.getClientById(id);

        Assertions.assertEquals(testClient.getFirstName(),client.getFirstName());
        System.out.println("The client retrieved: "+client);

    }

    @Test
    @Order(3)
    void update_client(){
        Client client = cdao.getClientById(testClient.getClientID());
        client.setLastName("Smith");
        cdao.updateClient(client);

        Client updatedClient = cdao.getClientById(testClient.getClientID());
        System.out.println(updatedClient);
        Assertions.assertEquals("Smith",updatedClient.getLastName());

    }

    @Test
    @Order(4)
    void get_all_clients(){
        Client c1 = new Client(0,"Bob","Mills");
        Client c2 = new Client(0,"Adam","Ranieri");
        Client c3 = new Client(0,"Jon","Jones");

        cdao.createClient(c1);
        cdao.createClient(c2);
        cdao.createClient(c3);

        Set<Client> allClients = cdao.getAllClients();
        System.out.println(allClients);
        Assertions.assertTrue(allClients.size()>2);
    }

    @Test
    @Order(5)
    void delete_book_by_id(){
        int id = testClient.getClientID();
        boolean result = cdao.deleteClientById(id);
        Assertions.assertTrue(result);
    }
}
