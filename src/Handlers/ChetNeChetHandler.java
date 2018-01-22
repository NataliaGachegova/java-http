package Handlers;

import Http.Request;

import java.io.IOException;
import java.net.Socket;

import ChetNeChet.*;

public class ChetNeChetHandler extends Handler {
    public ChetNeChetHandler(Socket s, Request r) throws IOException {
        super(s, r);
    }

    @Override
    public byte[] makeWork() throws Exception {
        String body = request.readLine();
        ChetNeChet data = new ChetNeChet(body);
        return data.getResult().getBytes();
    }
}
