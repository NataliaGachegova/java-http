
import Database.HibernateUtil;
        import Handlers.*;
        import Http.Request;

        import java.io.*;
        import java.net.ServerSocket;
        import java.net.Socket;

public class HTTPServer {

    public static void main(String[] args) throws Throwable {
        HibernateUtil.fillDatabase();
        ServerSocket ss = new ServerSocket(8080);
        System.out.println("HTTP Server has started on port 8080");
        while(true) {
            Socket s = ss.accept();
            System.err.println("Client accepted");
            new Thread(new SocketProcessor(s)).start();
        }

    }

    private static class SocketProcessor implements Runnable {
        private Socket s;
        private InputStream is;
        private OutputStream os;

        private SocketProcessor(Socket s) throws Throwable {
            this.s = s;
            this.is = s.getInputStream();
            this.os = s.getOutputStream();
        }

        public void run() {
            try {
                Request r = new Request(s);
                r.readHeaders();
                chooseHandler(r);
            } catch (Throwable t) {}
            System.err.println("Client processing finished");
        }

        private void chooseHandler(Request r) throws IOException {
            Handler h;
            if(r.getUrl().equalsIgnoreCase("/collections/clients")) {
                h = new ClientsHandler(s, r);
            } else if(r.getUrl().equalsIgnoreCase("/collections/orders")) {
                h = new OrdersHandler(s, r);
            } else if(r.getUrl().equalsIgnoreCase("/collections/seasonTickets")) {
                h = new SeasonTicketsHandler(s, r);
            } else if(r.getUrl().equalsIgnoreCase("/collections/trainings")) {
                h = new TrainingsHandler(s, r);
            } else if(r.getUrl().equalsIgnoreCase("/collections/trainers")) {
                h = new TrainersHandler(s, r);
            } else if(r.getUrl().equalsIgnoreCase("/collections/sportPrograms")) {
                h = new SportProgramsHandler(s, r);
            } else if(r.getMethod().equalsIgnoreCase("POST") && r.getUrl().equalsIgnoreCase("/chet-ne-chet")) {
                h = new ChetNeChetHandler(s, r);
            } else if(FileHandler.fileExist(r) && !r.getUrl().equalsIgnoreCase("/")) {
                h = new FileHandler(s, r);
            } else {
                h = new RedirectHandler(s, r);
            }
            h.handle();
        }
    }
}
