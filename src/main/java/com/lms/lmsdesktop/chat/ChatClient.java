package com.lms.lmsdesktop.chat;

import javafx.scene.control.TextArea;

import java.io.*;
import java.net.Socket;

public class ChatClient {
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String username;
    private TextArea chatArea;

    public ChatClient(String username, TextArea chatArea) {
        this.username = username;
        this.chatArea = chatArea;
    }

    public void connect() {
        try {
            // Create a new socket with the server IP address and port number
            socket = new Socket("localhost", 2200);

            // Initialize the output and input streams for the socket
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());

            // Send the username to the server
            output.writeObject(username);

            // Start a new thread to listen for messages from the server
            new Thread(() -> {
                try {
                    while (true) {
                        String message = (String) input.readObject();
                        chatArea.appendText(message + "\n");
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            // Close the socket, output stream, and input stream
            socket.close();
            output.close();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        try {
            // Send the message to the server via the output stream
            output.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
