package socket_with_thread;


import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public Client(int port) throws Exception {
        Socket socket = new Socket("localhost", port);
        Scanner scanner = new Scanner(System.in);

        BufferedReader clientreader = new BufferedReader(
                new InputStreamReader(
                        socket.getInputStream()));
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());

        String msgFromServer;
        while (!"stop".equals(msgFromServer = clientreader.readLine())) {

            System.out.println("msg from server = " + msgFromServer);
            String serverMsg = scanner.nextLine();
            printWriter.println(serverMsg);
            printWriter.flush();

            Thread.sleep(1000);
        }
    }
    public static void main(String[] args) throws Exception {
        new Client(Server.PORT);
    }
}





