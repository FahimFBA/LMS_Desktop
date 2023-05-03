package com.lms.lmsdesktop.admin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Paths;

public class MainApp extends Application {

    private static Stage primaryStage;

    public void start(Stage primaryStage) {
        MainApp.primaryStage = primaryStage;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SavedData.fxml"));
            Parent root = loader.load();
            SaveDataController controller = loader.getController();
            FileStorageAdmin fileStorageAdmin = FileStorageAdmin.getInstance(
                    Paths.get(System.getProperty("user.home"), "my-storage-directory").toString());
            controller.setFileStorage(fileStorageAdmin); // set file storage instance to the controller
            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Saved Data");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
