package Handlers;

import Database.HibernateUtil;
import Entities.JSONutils;
import Entities.SportProgram;
import Entities.Trainer;
import Entities.Training;
import Http.Request;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class TrainingsHandler extends CollectionHandler {
    public TrainingsHandler(Socket s, Request r) throws IOException {
        super(s, r);
    }

    @Override
    protected byte[] getCollection() {
        List<Training> trainings = HibernateUtil.getCollection(Training.class);
        return JSONutils.getArray(trainings, true).getBytes();
    }

    @Override
    protected byte[] createInstanceIntoCollection() throws IOException {
        String[] data = request.readLine().split(";");
        Training training = new Training(null, data[0]);
        training.setProgram(HibernateUtil.getObject(SportProgram.class, Integer.parseInt(data[1])));
        training.setTrainer(HibernateUtil.getObject(Trainer.class, Integer.parseInt(data[2])));
        HibernateUtil.createObject(training);
        return new byte[0];
    }

    @Override
    protected byte[] saveInstanceIntoCollection() throws IOException {
        String[] data = request.readLine().split(";");
        Training training = HibernateUtil.getObject(Training.class, Integer.parseInt(data[0]));
        training.setName(data[1]);
        training.setProgram(HibernateUtil.getObject(SportProgram.class, Integer.parseInt(data[2])));
        training.setTrainer(HibernateUtil.getObject(Trainer.class, Integer.parseInt(data[3])));
        HibernateUtil.updateObject(training);
        return new byte[0];
    }

    @Override
    protected byte[] deleteInstance() throws IOException {
        String data = request.readLine();
        Training training = HibernateUtil.getObject(Training.class, Integer.parseInt(data));
        HibernateUtil.deleteObject(training);
        return new byte[0];
    }
}
