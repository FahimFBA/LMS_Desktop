package com.lms.chatapp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatServer {

    private static final int MAX_CLIENTS_PER_CHATROOM = 10;
    private int port;
    private ServerSocket serverSocket;
    private Map<String, List<PrintWriter>> chatRooms;
    private List<ChatClientHandler> clientHandlers;

    public ChatServer(int port) {
        this.port = port;
        chatRooms = new HashMap<>();
        clientHandlers = new ArrayList<>();
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Chat server started on port " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected");
                ChatClientHandler clientHandler = new ChatClientHandler(clientSocket, this);
                clientHandlers.add(clientHandler);
                clientHandler.start();
            }
        } catch (IOException e) {
            System.out.println("Error starting server: " + e.getMessage());
        }
    }

    //    public synchronized void broadcast(String message, ChatClientHandler sender) {
//        for (ChatClientHandler clientHandler : clientHandlers) {
//            if (clientHandler != sender) {
//                try {
//                    clientHandler.sendMessage(message);
//                } catch (IOException e) {
//                    System.err.println("Error sending message to client: " + e.getMessage());
//                }
//            }
//        }
//    }
    public void broadcast(String chatRoomName, String message, ChatClientHandler sender, String extraData) {
        List<PrintWriter> chatRoomWriters = chatRooms.get(chatRoomName);
        if (chatRoomWriters != null) {
            for (PrintWriter writer : chatRoomWriters) {
                if (writer != sender.getOutput()) {
                    writer.println(message + " " + extraData);
                }
            }
        }
    }



    public synchronized void addClient(ChatClientHandler clientHandler) {
        clientHandlers.add(clientHandler);
    }

    public synchronized void removeClient(ChatClientHandler clientHandler, String chatRoom) {
        List<PrintWriter> chatRoomWriters = chatRooms.get(chatRoom);
        if (chatRoomWriters != null) {
            chatRoomWriters.remove(clientHandler.getOutput());
            clientHandler.setChatRoom(null);
            if (chatRoomWriters.isEmpty()) {
                chatRooms.remove(chatRoom);
            }
        }
        clientHandlers.remove(clientHandler);
    }

    public synchronized boolean joinChatRoom(String chatRoomName, PrintWriter writer) {
        List<PrintWriter> chatRoomWriters = chatRooms.get(chatRoomName);
        if (chatRoomWriters == null) {
            chatRoomWriters = new ArrayList<>();
            chatRooms.put(chatRoomName, chatRoomWriters);
        }
        if (chatRoomWriters.size() >= MAX_CLIENTS_PER_CHATROOM) {
            return false;
        }
        chatRoomWriters.add(writer);
        return true;
    }

    public synchronized void leaveChatRoom(String chatRoom, ChatClientHandler clientHandler) throws IOException {
        List<PrintWriter> chatRoomWriters = chatRooms.get(chatRoom);
        if (chatRoomWriters != null) {
            chatRoomWriters.remove(clientHandler.getOutput());
            if (chatRoomWriters.isEmpty()) {
                chatRooms.remove(chatRoom);
            }
        }
        clientHandler.setChatRoom(null);
        clientHandler.sendMessage("You have left the chat room '" + chatRoom + "'.");
    }

    public synchronized int getClientCount() {
        return clientHandlers.size();
    }

    public static void main(String[] args) {
        int port = 2000;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid port number, using default port " + port);
            }
        }

        ChatServer chatServer = new ChatServer(port);
        chatServer.start();
    }
}
