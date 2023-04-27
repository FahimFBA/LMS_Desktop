package com.lms.lmsdesktop.chat;

public class ChatServerMain {
    public static void main(String[] args) {
        // Create a new Server object and start the server
        Server server = new Server(2200);
        server.start();
    }
}
