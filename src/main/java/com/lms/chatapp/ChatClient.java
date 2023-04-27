package com.lms.chatapp;

import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import static java.lang.System.out;

public class ChatClient {
    private String serverAddress;
    private int serverPort;
    private String username;
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private ChatController chatController;
    private String profilePictureBase64;

    public ChatClient(String serverAddress, int serverPort, String username, String profilePicturePath, ChatController chatController) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.username = username;
        this.chatController = chatController;
        if (profilePicturePath != null && !profilePicturePath.isEmpty()) {
            try {
                byte[] profilePictureBytes = Files.readAllBytes(Paths.get(profilePicturePath));
                this.profilePictureBase64 = Base64.getEncoder().encodeToString(profilePictureBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void requestChatRooms() {
        out.println("REQUEST_ROOMS");
    }


    public void connect(String serverAddress, int serverPort, String username) {
        try {
            socket = new Socket(serverAddress, serverPort);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            output.println(username + " " + profilePictureBase64);

            // rest of the code...
            Thread listener = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            String line = input.readLine();
                            if (line.startsWith("MESSAGE")) {
                                String message = line.substring(8);
                                chatController.displayMessage(message);
                            } else if (line.startsWith("CHATROOMS")) {
                                String chatRoomsStr = line.substring(10);
                                String[] chatRooms = chatRoomsStr.split(",");
                                Platform.runLater(() -> chatController.updateChatRooms(chatRooms));
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            listener.start();
        } catch (IOException e) {
            e.printStackTrace();
            chatController.displayError("Unable to connect to the server.");
        }
    }



    public void sendMessage(String message, String chatRoom) {
        output.println("MESSAGE " + chatRoom + " " + message + " " + profilePictureBase64);
    }


    public void joinChatRoom(String chatRoom) {
        output.println("/join " + chatRoom);
    }


    public void leaveChatRoom(String chatRoom) {
        output.println("LEAVE " + chatRoom);
    }

    public void getChatRooms() {
        output.println("GET_CHATROOMS");
    }

    public void disconnect() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            chatController.displayError("Unable to disconnect from the server.");
        }
    }
    public void updateProfilePicture(String newProfilePictureBase64) {
        profilePictureBase64 = newProfilePictureBase64;
        output.println("UPDATE_PROFILE_PICTURE " + profilePictureBase64);
    }

    public ChatController getChatController() {
        return chatController;
    }

    public void connect() {
        connect(serverAddress, serverPort, username);
    }
}
