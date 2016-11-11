package socket_with_thread;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServersSocketThread extends Thread {
    Socket socket;
    BufferedReader serverreader;
    PrintWriter printWriter;

    //Socket clientSocket;

    public ServersSocketThread(Socket s) throws IOException {
        socket = s;
        serverreader = new BufferedReader(
                new InputStreamReader(
                        socket.getInputStream()));
        printWriter = new PrintWriter(socket.getOutputStream(), true);
        printWriter.println("hello from server");
        printWriter.flush();

        start();
    }

    @Override
    public void run() {

        try {
            String msgFromClient;
            while (!"stop".equals(msgFromClient = serverreader.readLine())) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("msg from client = " + msgFromClient);
                String serverMsg = scanner.nextLine();
                printWriter.println(serverMsg);
                printWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket s = new ServerSocket(9999);
        System.out.println(" Server Started ");
        try {
            while (true) {
                Socket socket = s.accept();
                try {
                    new ServersSocketThread(socket);
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            s.close();
        }


    }
}