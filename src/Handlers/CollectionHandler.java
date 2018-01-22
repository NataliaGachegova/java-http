package Handlers;

import Http.Request;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Natalya on 16.12.2016.
 */
public abstract class CollectionHandler extends Handler {
    public CollectionHandler(Socket s, Request r) throws IOException {
        super(s, r);
    }

    @Override
    public byte[] makeWork() throws Exception {
        if(request.getMethod().equalsIgnoreCase("GET")) {
            setContentType("application/json");
            return getCollection();
        } else if(request.getMethod().equalsIgnoreCase("POST")) {
            return createInstanceIntoCollection();
        } else if(request.getMethod().equalsIgnoreCase("PUT")) {
            return saveInstanceIntoCollection();
        } else if(request.getMethod().equalsIgnoreCase("DELETE")) {
            return deleteInstance();
        }
        return new RedirectHandler(socket, request).makeWork();
    }

    protected abstract byte[] getCollection();
    protected abstract byte[] createInstanceIntoCollection() throws IOException;
    protected abstract byte[] saveInstanceIntoCollection() throws IOException;
    protected abstract byte[] deleteInstance() throws IOException;
}
