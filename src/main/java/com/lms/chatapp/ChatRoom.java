package com.lms.chatapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChatRoom {
    private String name;
    private List<ChatClientHandler> clientHandlers;
    private List<ChatClientHandler> clients;
    private final int MAX_CLIENTS_PER_CHATROOM = 10;
    private ChatController chatController;

    public ChatRoom(String name, ChatController chatController) {
        this.name = name;
        this.clientHandlers = new ArrayList<>();
        this.clients = new ArrayList<>();
        this.chatController = chatController;
    }


    public ChatRoom(String name) {
        this.name = name;
        this.clients = new ArrayList<>();
        this.clientHandlers = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public boolean isEmpty() {
        return clients.isEmpty();
    }

    public void broadcastMessage(String message, ChatClientHandler sender) throws IOException {
        for (ChatClientHandler client : clients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }

    public boolean addClient(ChatClientHandler clientHandler) {
        if (clientHandlers.size() >= MAX_CLIENTS_PER_CHATROOM) {
            return false;
        }
        clientHandlers.add(clientHandler);
        clients.add(clientHandler);
        return true;
    }

    public void removeClient(ChatClientHandler clientHandler) {
        clientHandlers.remove(clientHandler);
        clients.remove(clientHandler);
    }

    public int getClientCount() {
        synchronized (clientHandlers) {
            return clientHandlers.size();
        }
    }
}
