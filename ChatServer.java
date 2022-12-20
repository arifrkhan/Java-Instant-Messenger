import java.io.*;
import java.net.*;

public class ChatServer {
    public static void main(String[] args) throws IOException {
        // Create a server socket
        ServerSocket serverSocket = new ServerSocket(9090);
        System.out.println("Server started on port 9090");

        // Listen for client connections
        Socket client1 = serverSocket.accept();
        System.out.println("Client 1 connected");
        Socket client2 = serverSocket.accept();
        System.out.println("Client 2 connected");

        // Create input and output streams for each client
        BufferedReader in1 = new BufferedReader(new InputStreamReader(client1.getInputStream()));
        PrintWriter out1 = new PrintWriter(client1.getOutputStream(), true);
        BufferedReader in2 = new BufferedReader(new InputStreamReader(client2.getInputStream()));
        PrintWriter out2 = new PrintWriter(client2.getOutputStream(), true);

        // Send a welcome message to each client
        out1.println("Welcome to the chat server! You are connected to Client 2.");
        out2.println("Welcome to the chat server! You are connected to Client 1.");

        // Create a thread to read messages from each client and relay them to the other
        new Thread(new Runnable() {
            public void run() {
                try {
                    String message;
                    while ((message = in1.readLine()) != null) {
                        out2.println(message);
                    }
                } catch (IOException e) {
                    System.out.println("Error reading from client 1: " + e);
                }
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                try {
                    String message;
                    while ((message = in2.readLine()) != null) {
                        out1.println(message);
                    }
                } catch (IOException e) {
                    System.out.println("Error reading from client 2: " + e);
                }
            }
        }).start();
    }
}
