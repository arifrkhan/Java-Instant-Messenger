import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient1 {
    public static void main(String[] args) throws IOException {
        // Prompt the user for a username
        Scanner in = new Scanner(System.in);
        System.out.print("Enter a username: ");
        String username = in.nextLine();
        System.out.print("Enter password:");
        String password = in.nextLine();
        if(username.equals(username) && password.equals(password))
        {
            System.out.println("Authentication Successful");
        }

        // Connect to the chat server
        Socket socket = new Socket("localhost", 9090);
        System.out.println(username + " Connected to the chat server");

        // Create input and output streams for the socket
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

        // Start a thread to read messages from the server and print them to the screen
        new Thread(new Runnable() {
            public void run() {
                try {
                    String message;
                    while ((message = input.readLine()) != null) {
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    System.out.println("Error reading from server: " + e);
                }
            }
        }).start();

        // Read messages from the user and send them to the server
        while (true) {
            String message = in.nextLine();
            output.println(username + ": " + message);
        }
    }
}
