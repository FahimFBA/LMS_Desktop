package com.lms.lmsdesktop.chat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ChatClientMain extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the ChatUI.fxml file
        Parent root = FXMLLoader.load(getClass().getResource("ChatUI.fxml"));

        // Create a new Scene with the loaded FXML file
        Scene scene = new Scene(root);

        // Set the Scene on the Stage
        primaryStage.setScene(scene);

        // Show the Stage
        primaryStage.show();

        // Get a reference to the chat area in the UI
        TextArea chatArea = (TextArea) scene.lookup("#chatArea");

        // Create a new ChatClient object and connect to the server
        ChatClient client = new ChatClient("username", chatArea);
        client.connect();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
