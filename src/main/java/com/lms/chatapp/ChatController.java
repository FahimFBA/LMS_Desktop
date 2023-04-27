package com.lms.chatapp;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Base64;

public class ChatController {
    @FXML
    private Button changeProfilePictureButton;



    @FXML
    private TextArea chatArea;

    @FXML
    private Label chatRoomLabel;

    @FXML
    private ImageView chatRoomPicture;

    @FXML
    private ListView<?> chatRoomsListView;

    @FXML
    private TextField chatTextArea;

    @FXML
    private Button connectButton;

    @FXML
    private Button disconnectButton;

    @FXML
    private Button joinChatRoomButton;

    @FXML
    private ImageView profilePicture;

    @FXML
    private Button sendMessageButton;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField messageTextField;


    @FXML private ImageView profilePictureImageView;

    private ChatClient chatClient;
    private String currentChatRoom;

    public void initialize() {
        // Enable the chat room list view and message text field
        chatRoomsListView.setDisable(true);
        messageTextField.setDisable(true);
        sendMessageButton.setDisable(true);
        joinChatRoomButton.setDisable(true);
        disconnectButton.setDisable(true);
        changeProfilePictureButton.setDisable(true);



        chatRoomsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // Set up the message text field to send messages on enter key press
        messageTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                sendMessage();
            }
        });
    }

    public void setChatClient(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @FXML
    public void connectButtonAction(ActionEvent event) throws IOException {
        String username = usernameField.getText().trim();
        if (!username.isEmpty()) {
            chatClient = new ChatClient("localhost", 2000, username, "", this);
            setChatClient(chatClient);
            chatClient.connect();
            usernameField.setDisable(true);
            connectButton.setDisable(true);
            chatRoomsListView.setDisable(false);
            messageTextField.setDisable(false);
            sendMessageButton.setDisable(false);
            joinChatRoomButton.setDisable(false);
            disconnectButton.setDisable(false);
            changeProfilePictureButton.setDisable(false);
        }
    }



    public void displayMessage(String message) {
        Platform.runLater(() -> chatTextArea.appendText(message + "\n"));
    }

    public void updateChatRooms(String[] chatRooms) {
        Platform.runLater(() -> {
            chatRoomsListView.getItems().clear();

            System.out.println("Chat rooms: " + Arrays.toString(chatRooms));
        });
    }


    @FXML
    private void sendMessage() {
        String message = messageTextField.getText().trim();
        if (!message.isEmpty()) {
            if (currentChatRoom == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a chat room.");
                alert.showAndWait();
                return;
            }
            chatClient.sendMessage(currentChatRoom, message);
            messageTextField.clear();
        }
    }

    @FXML
    private void joinChatRoom() {
        String chatRoom = (String) chatRoomsListView.getSelectionModel().getSelectedItem();
        if (chatRoom != null) {
            chatClient.joinChatRoom(chatRoom);
            currentChatRoom = chatRoom;
            chatTextArea.clear();
            chatRoomLabel.setText("Chat Room: " + chatRoom);
        } else {
            new Alert(Alert.AlertType.ERROR, "Please select a chat room.").showAndWait();
        }
    }
    @FXML
    private void sendMessageAction(ActionEvent event) {
        sendMessage();
    }



    public void displayError(String errorMessage) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(errorMessage);
            alert.showAndWait();
        });
    }

    @FXML
    private void disconnectButtonAction(ActionEvent event) throws IOException {
        chatClient.disconnect();
        Platform.exit();
    }

    public void changeProfilePicture(File newProfilePictureFile) {
        try {
            byte[] profilePictureBytes = Files.readAllBytes(newProfilePictureFile.toPath());
            String profilePictureBase64 = Base64.getEncoder().encodeToString(profilePictureBytes);
            chatClient.updateProfilePicture(profilePictureBase64);

            Image newProfilePicture = new Image(new ByteArrayInputStream(profilePictureBytes));
            profilePicture.setImage(newProfilePicture);
        } catch (IOException e) {
            displayError("Error changing profile picture: " + e.getMessage());
        }
    }

    public void updateProfilePicture(String profilePictureBase64) {
        byte[] profilePictureBytes = Base64.getDecoder().decode(profilePictureBase64);
        Image newProfilePicture = new Image(new ByteArrayInputStream(profilePictureBytes));
        profilePicture.setImage(newProfilePicture);
    }

    @FXML
    private void joinChatRoomButtonAction(ActionEvent event) {
        joinChatRoom();
    }
    @FXML
    public void changeProfilePictureButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Profile Picture");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File newProfilePictureFile = fileChooser.showOpenDialog(changeProfilePictureButton.getScene().getWindow());
        if (newProfilePictureFile != null) {
            changeProfilePicture(newProfilePictureFile);
        }
    }
}
