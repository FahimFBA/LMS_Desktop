package com.lms.lmsdesktop.chat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

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
    private ListView<String> chatRoomsListView;

    @FXML
    private TextField chatTextArea;

    @FXML
    private Button connectButton;

    @FXML
    private Button disconnectButton;

    @FXML
    private ImageView profilePicture;

    @FXML
    private Button sendMessageButton;

    @FXML
    private TextField usernameField;

    // Define a ChatClient variable
    private ChatClient client;

    @FXML
    void changeProfilePictureButtonAction(ActionEvent event) {
        // TODO: Implement profile picture change logic
    }

    @FXML
    void connectButtonAction(ActionEvent event) {
        // Get the username from the text field
        String username = usernameField.getText();

        // Create a new ChatClient with the username
        client = new ChatClient(username, chatArea);

        // Connect the client to the server
        client.connect();

        // Disable the connect button and enable the disconnect button
        connectButton.setDisable(true);
        disconnectButton.setDisable(false);
    }

    @FXML
    void disconnectButtonAction(ActionEvent event) {
        // Disconnect the client from the server
        client.disconnect();

        // Enable the connect button and disable the disconnect button
        connectButton.setDisable(false);
        disconnectButton.setDisable(true);
    }

    @FXML
    void sendMessageAction(ActionEvent event) {
        // Get the text from the chat text area
        String messageText = chatTextArea.getText();

        // Create a new ChatMessage object with the message text and type
        ChatMessage message = new ChatMessage(usernameField.getText(), messageText, ChatMessage.MessageType.MESSAGE);

        // Clear the chat text area
        chatTextArea.clear();

        // Append the message to the chat area
        chatArea.appendText(message.toString());
    }

}
