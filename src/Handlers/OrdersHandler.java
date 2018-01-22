package Handlers;

import Database.HibernateUtil;
import Entities.Client;
import Entities.JSONutils;
import Entities.Order;
import Entities.SeasonTicket;
import Http.Request;

import java.io.IOException;
import java.net.Socket;
import java.util.List;


public class OrdersHandler extends CollectionHandler {
    public OrdersHandler(Socket s, Request r) throws IOException {
        super(s, r);
    }

    @Override
    protected byte[] getCollection() {
        List<Order> orders = HibernateUtil.getCollection(Order.class);
        return JSONutils.getArray(orders, true).getBytes();
    }

    @Override
    protected byte[] createInstanceIntoCollection() throws IOException {
        String[] data = request.readLine().split(";");
        Order order = new Order(null, data[0].equalsIgnoreCase("true"));
        order.setClient(HibernateUtil.getObject(Client.class, Integer.parseInt(data[1])));
        order.setTicket(HibernateUtil.getObject(SeasonTicket.class, Integer.parseInt(data[2])));
        HibernateUtil.createObject(order);
        return new byte[0];
    }

    @Override
    protected byte[] saveInstanceIntoCollection() throws IOException {
        String[] data = request.readLine().split(";");
        Order order = HibernateUtil.getObject(Order.class, Integer.parseInt(data[0]));
        order.setPaid(data[1].equalsIgnoreCase("true"));
        order.setClient(HibernateUtil.getObject(Client.class, Integer.parseInt(data[2])));
        order.setTicket(HibernateUtil.getObject(SeasonTicket.class, Integer.parseInt(data[3])));
        HibernateUtil.updateObject(order);
        return new byte[0];
    }

    @Override
    protected byte[] deleteInstance() throws IOException {
        String data = request.readLine();
        Order order = HibernateUtil.getObject(Order.class, Integer.parseInt(data));
        HibernateUtil.deleteObject(order);
        return new byte[0];
    }
}
