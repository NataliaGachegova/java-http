package Handlers;

import Http.Request;
import jdk.nashorn.internal.ir.RuntimeNode;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Natalya on 16.12.2016.
 */
public abstract class Handler {
    private int status = 200;
    private String contentType = "text/plain";
    private List<String> headers = new ArrayList<>();

    protected Socket socket;
    protected Request request;
    private OutputStream os;

    public Handler(Socket s, Request r) throws IOException {
        socket = s;
        request = r;

        os = s.getOutputStream();
    }

    public void handle() {
        try(Socket s = socket) {
            byte[] responseData = makeWork();

            String headers = "HTTP/1.1 " + status + "\n" +
                    "Server: YarServer/2016-10-10\n" +
                    "Content-Type: " + contentType + " \n";
            for (String header : this.headers) {
                headers += header + "\n";
            }
            headers += "Connection: close\n\n";


            os.write(headers.getBytes());
            os.write(responseData);
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
            String headers = "HTTP/1.1 500 Internal Server Error\n\n";
            try {
                os.write(headers.getBytes());
                os.flush();
            } catch (IOException ioe) {
                e.printStackTrace();
            }
        }
    }

    public abstract byte[] makeWork() throws Exception;

    public void addHeader(String header) {
        headers.add(header);
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
