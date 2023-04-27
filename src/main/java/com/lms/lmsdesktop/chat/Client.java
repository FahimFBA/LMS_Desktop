package com.lms.lmsdesktop.chat;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    public Client(String serverAddress, int serverPort) {
        try {
            // Create a new socket with the server address and port number
            socket = new Socket(serverAddress, serverPort);

            // Initialize the input and output streams for the socket
            input = new ObjectInputStream(socket.getInputStream());
            output = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(ChatMessage message) {
        try {
            // Send the ChatMessage object to the server via the output stream
            output.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            // Close the socket, input stream, and output stream
            socket.close();
            input.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
