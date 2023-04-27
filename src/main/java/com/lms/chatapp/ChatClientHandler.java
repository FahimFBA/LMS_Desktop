package com.lms.chatapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClientHandler extends Thread {
    private final Socket socket;
    private final ChatServer chatServer;
    private String username;
    private String chatRoom;
    private String profilePictureBase64; // Add this line for profile picture
    private PrintWriter output;
    public ChatClientHandler(Socket socket, ChatServer chatServer) {
        this.socket = socket;
        this.chatServer = chatServer;
        chatRoom = null;
        profilePictureBase64 = ""; // Add this line for profile picture
    }
    // Add the getter and setter methods for the profile picture
    public String getProfilePictureBase64() {
        return profilePictureBase64;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setProfilePictureBase64(String profilePictureBase64) {
        this.profilePictureBase64 = profilePictureBase64;
    }
    public void setChatRoom(String chatRoom) {
        this.chatRoom = chatRoom;
    }

    public String getUsername() {
        return username;
    }

    public String getChatRoom() {
        return chatRoom;
    }

    public PrintWriter getOutput() {
        return output;
    }


    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);

            output.println("Welcome to the chat server! Please enter your username:");
            username = input.readLine();
            chatServer.addClient(this);
            output.println("Hello, " + username + "!");

            String inputLine;
            while ((inputLine = input.readLine()) != null) {
                if (inputLine.equals("quit")) {
                    break;
                }

                String[] parts = inputLine.split("\\s+", 2);
                String command = parts[0];
                String data = parts.length > 1 ? parts[1] : "";

                if (chatRoom == null) {
                    if (command.equals("/join") && !data.isEmpty()) {
                        if (chatServer.joinChatRoom(data, output)) {
                            chatRoom = data;
                            output.println("Joined chat room '" + data + "'.");
                        } else {
                            output.println("Failed to join chat room '" + data + "'.");
                        }
                    } else {
                        output.println("You are not currently in a chat room. Use '/join <chatRoomName>' to join a chat room.");
                    }
                } else {
                    if (command.equals("/leave")) {
                        chatServer.leaveChatRoom(chatRoom, this);
                        chatRoom = null;
                    } else if (command.equals("/updateProfilePicture")) {
                        setProfilePictureBase64(data);
                        chatServer.broadcast(chatRoom, "UPDATE_PROFILE_PICTURE", this, data);
                    } else {
                        String message = inputLine;
                        if (!profilePictureBase64.isEmpty()) {
                            message += " " + profilePictureBase64;
                        }
                        chatServer.broadcast(chatRoom, "MESSAGE", this, username + ": " + message);
                    }
                }
            }

            chatServer.removeClient(this, chatRoom);
            if (chatRoom != null) {
                chatServer.leaveChatRoom(chatRoom, this);
            }
            socket.close();
        } catch (IOException e) {
            System.err.println("Error handling client: " + e);
        }
    }

    public void sendMessage(String message) throws IOException {
        output.println(message);
    }
}

