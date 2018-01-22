package Handlers;

import Database.HibernateUtil;
import Entities.Client;
import Entities.JSONutils;
import Http.Request;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

/**
 * Created by Natalya on 18.12.2016.
 */
public class ClientsHandler extends CollectionHandler {
    public ClientsHandler(Socket s, Request r) throws IOException {
        super(s, r);
    }

    @Override
    protected byte[] getCollection() {
        List<Client> clients = HibernateUtil.getCollection(Client.class);
        return JSONutils.getArray(clients, true).getBytes();
    }

    @Override
    protected byte[] createInstanceIntoCollection() throws IOException {
        String[] data = request.readLine().split(";");
        Client client = new Client(null, data[0], data[1]);
        HibernateUtil.createObject(client);
        return new byte[0];
    }

    @Override
    protected byte[] saveInstanceIntoCollection() throws IOException {
        String[] data = request.readLine().split(";");
        Client client = HibernateUtil.getObject(Client.class, Integer.parseInt(data[0]));
        client.setName(data[1]);
        client.setPhoneNumber(data[2]);
        HibernateUtil.updateObject(client);
        return new byte[0];
    }

    @Override
    protected byte[] deleteInstance() throws IOException {
        String data = request.readLine();
        Client client = HibernateUtil.getObject(Client.class, Integer.parseInt(data));
        HibernateUtil.deleteObject(client);
        return new byte[0];
    }
}
