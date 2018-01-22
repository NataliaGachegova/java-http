package Handlers;

import Http.Request;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;

/**
 * Created by Natalya on 16.12.2016.
 */
public class FileHandler extends Handler {
    public FileHandler(Socket s, Request r) throws IOException {
        super(s, r);
    }
    public static boolean fileExist(Request r) {
        String filename = "C:\\Users\\Nataly\\IdeaProjects\\LabRab4\\src\\html" +  r.getUrl();
        File file = new File(filename);
        return file.exists();
    }

    @Override
    public byte[] makeWork() throws Exception {
        String filename = "C:\\Users\\Nataly\\IdeaProjects\\LabRab4\\src\\html" +  request.getUrl();
        String[] strings = filename.split("\\.");
        String format = (String) strings[1];
        switch (format){
            case "html":
                setContentType("text/html");
                break;
            case "css":
                setContentType("text/css");
                break;
            case "js":
                setContentType("application/javascript");
                break;
        }

        return Files.readAllBytes(new File(filename).toPath());
    }
}
