package socket_with_thread;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static final int PORT = 9999;
    public Server(int port) throws IOException {
        ServerSocket ss = new ServerSocket(port);
        while(true) {
            new ServerThread(ss.accept());
        }
    }
    private class ServerThread extends Thread {
        private final Socket socket;
        public ServerThread(Socket socket) {
            this.socket = socket;
            start();
        }
        public void run() {
            try {
                Scanner scanner = new Scanner(System.in);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
                printWriter.println("hello from server");
                printWriter.flush();
                String msgFromClient;
                while (!"stop".equals(msgFromClient = bufferedReader.readLine())) {

                    System.out.println("msg from client = " + msgFromClient);
                    String serverMsg = scanner.nextLine();
                    printWriter.println(serverMsg);
                    printWriter.flush();
                }
            } catch(Throwable t) {
                System.out.println("Caught " + t + " - closing thread");
            }
        }
    }
    public static void main(String[] args) throws IOException {

        ExecutorService service = Executors.newFixedThreadPool(4);
        new Server(PORT);
        service.shutdown();
    }
}