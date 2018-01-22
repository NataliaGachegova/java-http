package Handlers;

import Database.HibernateUtil;
import Entities.JSONutils;
import Entities.Trainer;
import Http.Request;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

/**
 * Created by Natalya on 18.12.2016.
 */
public class TrainersHandler extends CollectionHandler {
    public TrainersHandler(Socket s, Request r) throws IOException {
        super(s, r);
    }

    @Override
    protected byte[] getCollection() {
        List<Trainer> trainers = HibernateUtil.getCollection(Trainer.class);
        return JSONutils.getArray(trainers, true).getBytes();
    }

    @Override
    protected byte[] createInstanceIntoCollection() throws IOException {
        String[] data = request.readLine().split(";");
        Trainer trainer = new Trainer(null, data[0], Integer.parseInt(data[1]));
        HibernateUtil.createObject(trainer);
        return new byte[0];
    }

    @Override
    protected byte[] saveInstanceIntoCollection() throws IOException {
        String[] data = request.readLine().split(";");
        Trainer trainer = HibernateUtil.getObject(Trainer.class, Integer.parseInt(data[0]));
        trainer.setName(data[1]);
        trainer.setSalary(Integer.parseInt(data[2]));
        HibernateUtil.updateObject(trainer);
        return new byte[0];
    }

    @Override
    protected byte[] deleteInstance() throws IOException {
        String data = request.readLine();
        Trainer trainer = HibernateUtil.getObject(Trainer.class, Integer.parseInt(data));
        HibernateUtil.deleteObject(trainer);
        return new byte[0];
    }
}
