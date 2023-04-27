package com.lms.chatapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChatApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ChatUI.fxml"));
        Parent root = loader.load();

        ChatController chatController = loader.getController();
        ChatClient chatClient = new ChatClient("localhost", 2000, "John", "C:\\Users\\fahim\\IdeaProject\\LMS_Desktop\\src\\main\\resources\\com\\lms\\chatapp\\icons8-male-user-94.png", chatController);
        chatController.setChatClient(chatClient);

        primaryStage.setTitle("Chat App");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
