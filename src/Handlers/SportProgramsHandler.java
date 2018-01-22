package Handlers;

import Database.HibernateUtil;
import Entities.JSONutils;
import Entities.SportProgram;
import Http.Request;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class SportProgramsHandler extends CollectionHandler{
    public SportProgramsHandler(Socket s, Request r) throws IOException {
        super(s, r);
    }

    @Override
    protected byte[] getCollection() {
        List<SportProgram> sportPrograms = HibernateUtil.getCollection(SportProgram.class);
        return JSONutils.getArray(sportPrograms, true).getBytes();
    }

    @Override
    protected byte[] createInstanceIntoCollection() throws IOException {
        String[] data = request.readLine().split(";");
        SportProgram sportProgram = new SportProgram(null, data[0]);
        HibernateUtil.createObject(sportProgram);
        return new byte[0];
    }

    @Override
    protected byte[] saveInstanceIntoCollection() throws IOException {
        String[] data = request.readLine().split(";");
        SportProgram sportProgram = HibernateUtil.getObject(SportProgram.class, Integer.parseInt(data[0]));
        sportProgram.setName(data[1]);
        HibernateUtil.updateObject(sportProgram);
        return new byte[0];
    }

    @Override
    protected byte[] deleteInstance() throws IOException {
        String data = request.readLine();
        SportProgram sportProgram = HibernateUtil.getObject(SportProgram.class, Integer.parseInt(data));
        HibernateUtil.deleteObject(sportProgram);
        return new byte[0];
    }
}
