package com.lms.lmsdesktop.chat;


import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class ChatServer extends Application {

    private Stage primaryStage;
    private VBox serverLayout;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Server Chat");

        initServerLayout();
    }

    private void initServerLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ChatClient.class.getResource("ServerGUI.fxml"));
            ServerController serverController = new ServerController();
            loader.setController(serverController);
            serverLayout = (VBox) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(serverLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    // We need to eliminate the Server Threads
                    // If the User decides to close it.
                    if (serverController.server != null) {
                        serverController.server.stop();
                        serverController.server = null;
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
