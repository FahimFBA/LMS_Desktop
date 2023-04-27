package com.lms.chatapp;

import java.io.IOException;

public class ServerLauncher{
    public static void main(String[] args) throws IOException {
        // Set the server port
        int serverPort = 2000;

        // Create an instance of the ChatServer
        ChatServer server = new ChatServer(serverPort);

        // Start the server
        server.start();
    }
}
