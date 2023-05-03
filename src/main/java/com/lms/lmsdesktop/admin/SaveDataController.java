package com.lms.lmsdesktop.admin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;

public class SaveDataController {

    @FXML
    private VBox dataBox;

    @FXML
    private Button refreshButton;

    @FXML
    private Button closeButton;

    @FXML
    private Button addButton;

    private FileStorageAdmin fileStorage;

    @FXML
    public void initialize() {
        // Use the singleton instance of FileStorageAdmin
        fileStorage = FileStorageAdmin.getInstance("com.lms.lmsdesktop.admin.FileStorage");
        refreshData();
    }


    public void setFileStorage(FileStorageAdmin fileStorage) {
        this.fileStorage = fileStorage;
    }

    @FXML
    public void refreshData() {
        dataBox.getChildren().clear();
        List<File> files = fileStorage.listFiles();
        for (File file : files) {
            Label label = new Label(file.getName());

            Button deleteButton = new Button("Delete");
//            deleteButton.setOnAction(event -> {
//                String fileName = label.getText();
//                delete(fileName);
//            });

            dataBox.getChildren().add(new VBox(label, deleteButton));
        }
    }



    public FileStorageAdmin getFileStorage() {
        return fileStorage;
    }

    @FXML
    private void close() {
        Stage stage = MainApp.getPrimaryStage();
        if (stage != null) {
            stage.close();
        }
    }

    @FXML
    public void openFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a file to save");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File selectedFile = fileChooser.showSaveDialog(null);
        if (selectedFile != null) {
            String fileName = selectedFile.getName();
            int i = 1;
            while (fileStorage.resolve(fileName).toFile().exists()) {
                fileName = selectedFile.getName() + "_" + i;
                i++;
            }
            try {
                Files.copy(selectedFile.toPath(), fileStorage.resolve(fileName));
                refreshData();
            } catch (IOException e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Failed to save file");
                alert.setContentText("An error occurred while saving the file. Please try again.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    public void add() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a file to save");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File selectedFile = fileChooser.showSaveDialog(null);
        if (selectedFile != null) {
            try {
                File destFile = fileStorage.resolve(selectedFile.getName()).toFile();
                if (destFile.exists()) {
                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Dialog");
                    alert.setHeaderText("File already exists");
                    alert.setContentText("Do you want to replace the existing file?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        Files.copy(selectedFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    } else {
                        return;
                    }
                } else {
                    Files.copy(selectedFile.toPath(), destFile.toPath());
                }
                refreshData();
            } catch (IOException e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Failed to save file");
                alert.setContentText("An error occurred while saving the file. Please try again.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    public void deleteSelected() {
        List<Label> selectedFiles = dataBox.getChildren().stream()
                .filter(node -> node instanceof Label && ((Label) node).getStyleClass().contains("selected"))
                .map(node -> (Label) node)
                .collect(Collectors.toList());
        if (selectedFiles.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No files selected");
            alert.setContentText("Please select one or more files to delete.");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Delete selected files?");
        StringBuilder message = new StringBuilder("Are you sure you want to delete the following files?\n");
        for (Label file : selectedFiles) {
            message.append(file.getText()).append("\n");
        }
        alert.setContentText(message.toString());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            for (Label file : selectedFiles) {
                try {
                    fileStorage.delete(file.getText());
                    dataBox.getChildren().remove(file);
                } catch (IOException e) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Error");
                    errorAlert.setHeaderText("Failed to delete file");
                    errorAlert.setContentText("An error occurred while deleting the file. Please try again.");
                    errorAlert.showAndWait();
                }
            }
        }
    }
    @FXML
    public void delete(MouseEvent event) {
        // Get the label that was clicked
        Label label = (Label) event.getTarget();

        // Get the name of the file to delete
        String fileName = label.getText();

        // Get the singleton instance of FileStorageAdmin
        FileStorageAdmin fileStorageAdmin = FileStorageAdmin.getInstance("com/lms/lmsdesktop/admin/FileStorageAdmin");

        // Resolve the path to the file and delete it
        Path path = fileStorageAdmin.resolve(fileName);
        try {
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Refresh the data displayed in the UI
        refreshData();
    }



//    @FXML
//    public void selectFile(MouseEvent event) {
//        Label label = (Label) event.getSource();
//        if (label.getStyleClass().contains("selected")) {
//            label.getStyleClass().remove("selected");
//        } else {
//            label.getStyleClass().add("selected");
//        }
//    }

    @FXML
    public void clearSelection() {
        dataBox.getChildren().forEach(node -> node.getStyleClass().remove("selected"));
    }
}