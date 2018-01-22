package Http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Request {
    String method;
    String url;
    String protocol;
    List<String> headers = new ArrayList<>();

    Socket socket;
    BufferedReader reader;

    public Request(Socket socket) throws IOException {
        this.socket = socket;
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void readHeaders() throws Throwable {
        String line = reader.readLine();
        String[] data = line.split(" ");
        method = data[0];
        url = data[1];
        protocol = data[2];

        while(true) {
            line =  reader.readLine();
            System.out.println(line);
            if(line == null || line.trim().length() == 0) {
                break;
            }
            headers.add(line);
        }
    }

    public String getMethod() {
        return method;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getUrl() {
        return url;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public String readLine() throws IOException {
        return reader.readLine();
    }
}
