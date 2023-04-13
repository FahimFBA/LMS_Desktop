package com.lms.lmsdesktop.chatting;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ChatWindowOne extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Load the FXML file from the specified path
        URL fxmlUrl = getClass().getResource("chat-window-one-view.fxml");
        Parent root = FXMLLoader.load(fxmlUrl);

        // Set up the scene and show the stage
        Scene scene = new Scene(root);
        primaryStage.setTitle("Chat Window One");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
