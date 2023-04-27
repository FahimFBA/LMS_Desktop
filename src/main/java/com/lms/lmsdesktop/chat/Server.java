package com.lms.lmsdesktop.chat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private int port;
    private List<ClientHandler> clients = new ArrayList<>();
    private ServerSocket serverSocket;

    public Server(int port) {
        this.port = port;
    }

    public void start() {
        try {
            // Create a new ServerSocket with the specified port number
            serverSocket = new ServerSocket(port);

            System.out.println("Server started on port " + port);

            while (true) {
                // Wait for a client to connect and create a new ClientHandler for the client
                Socket clientSocket = serverSocket.accept();
                ClientHandler client = new ClientHandler(clientSocket);

                // Add the client to the list of clients
                clients.add(client);

                // Start a new thread to handle the client's messages
                new Thread(client).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            // Close the ServerSocket
            serverSocket.close();

            // Disconnect all clients
            for (ClientHandler client : clients) {
                client.disconnect();
            }

            System.out.println("Server stopped");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void broadcast(ChatMessage message) {
        // Send the ChatMessage object to all connected clients
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    private class ClientHandler implements Runnable {
        private Socket clientSocket;
        private String username;
        private ObjectInputStream input;
        private ObjectOutputStream output;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        public void disconnect() {
            try {
                // Close the client socket, input stream, and output stream
                clientSocket.close();
                input.close();
                output.close();

                // Remove this client from the list of clients
                clients.remove(this);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void sendMessage(ChatMessage message) {
            try {
                // Send the ChatMessage object to the client via the output stream
                output.writeObject(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                // Initialize the input and output streams for the client socket
                input = new ObjectInputStream(clientSocket.getInputStream());
                output = new ObjectOutputStream(clientSocket.getOutputStream());

                // Read the username from the client and store it in the instance variable
                username = (String) input.readObject();

                System.out.println(username + " connected");

                while (true) {
                    // Read a ChatMessage object from the client and broadcast it to all other clients
                    ChatMessage message = (ChatMessage) input.readObject();
                    broadcast(message);
                }

            } catch (IOException | ClassNotFoundException e) {
                System.out.println(username + " disconnected");
                disconnect();
            }
        }
    }
}
