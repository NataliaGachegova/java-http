package Handlers;

import Http.Request;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Natalya on 16.12.2016.
 */
public class RedirectHandler extends Handler {
    public RedirectHandler(Socket s, Request r) throws IOException {
        super(s, r);
    }

    @Override
    public byte[] makeWork() throws Exception {
        setStatus(302);
        addHeader("Location: /index.html");
        return new byte[]{};
    }
}
