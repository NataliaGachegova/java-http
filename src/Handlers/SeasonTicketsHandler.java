package Handlers;


import Database.HibernateUtil;
import Entities.JSONutils;
import Entities.SeasonTicket;
import Entities.Training;
import Http.Request;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class SeasonTicketsHandler extends CollectionHandler {
    public SeasonTicketsHandler(Socket s, Request r) throws IOException {
        super(s, r);
    }

    @Override
    protected byte[] getCollection() {
        List<SeasonTicket> seasonTickets = HibernateUtil.getCollection(SeasonTicket.class);
        return JSONutils.getArray(seasonTickets, true).getBytes();
    }

    @Override
    protected byte[] createInstanceIntoCollection() throws IOException {
        String[] data = request.readLine().split(";");
        SeasonTicket seasonTicket = new SeasonTicket(null, Integer.parseInt(data[0]), Integer.parseInt(data[1]));
        seasonTicket.setTraining(HibernateUtil.getObject(Training.class, Integer.parseInt(data[2])));
        HibernateUtil.createObject(seasonTicket);
        return new byte[0];
    }

    @Override
    protected byte[] saveInstanceIntoCollection() throws IOException {
        String[] data = request.readLine().split(";");
        SeasonTicket seasonTicket = HibernateUtil.getObject(SeasonTicket.class, Integer.parseInt(data[0]));
        seasonTicket.setPrice(Integer.parseInt(data[1]));
        seasonTicket.setMouth(Integer.parseInt(data[2]));
        seasonTicket.setTraining(HibernateUtil.getObject(Training.class, Integer.parseInt(data[3])));
        HibernateUtil.updateObject(seasonTicket);
        return new byte[0];
    }

    @Override
    protected byte[] deleteInstance() throws IOException {
        String data = request.readLine();
        SeasonTicket seasonTicket = HibernateUtil.getObject(SeasonTicket.class, Integer.parseInt(data));
        HibernateUtil.deleteObject(seasonTicket);
        return new byte[0];
    }
}