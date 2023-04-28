package com.lms.lmsdesktop.chat;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientController {
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnLogout;
    @FXML
    private TextArea txtAreaServerMsgs;
    @FXML
    private TextField txtHostIP;
    @FXML
    private TextField txtUsername;
    @FXML
    private ListView<String> listUser;
    @FXML
    private TextArea txtUserMsg;

    private ObservableList<String> users;

    // Server Configuration
    private boolean connected;
    private String server, username;
    private int port;

    // for I/O
    private ObjectInputStream sInput;		// to read from the socket
    private ObjectOutputStream sOutput;		// to write on the socket
    private Socket socket;

    public void login() {
        port = 1500;
        server = txtHostIP.getText();
        System.out.println(server);
        username = txtUsername.getText();
        // test if we can start the connection to the Server
        // if it failed nothing we can do
        if(!start())
            return;
        connected = true;
        btnLogin.setDisable(true);
        btnLogout.setDisable(false);
        txtUsername.setEditable(false);
        txtHostIP.setEditable(false);
    }
    /**
     * Method used by btnLogout from Java FX
     */
    public void logout() {
        if (connected) {
            ChatMessage msg = new ChatMessage(ChatMessage.LOGOUT, "");
            try {
                sOutput.writeObject(msg);
                txtUserMsg.setText("");
                btnLogout.setDisable(false);
                btnLogin.setDisable(true);
                txtUsername.setEditable(true);
                txtHostIP.setEditable(true);
            } catch (IOException e) {
                display("Exception writing to server: " + e);
            }
        }
    }
    /**
     * To send a message to the server
     */
    public void sendMessage() {
        if (connected) {
            ChatMessage msg = new ChatMessage(ChatMessage.MESSAGE, txtUserMsg.getText());
            try {
                sOutput.writeObject(msg);
                txtUserMsg.setText("");
            }
            catch(IOException e) {
                display("Exception writing to server: " + e);
            }
        }
    }

    /**
     * Sends message to server
     * Used by TextArea txtUserMsg to handle Enter key event
     */
    public void handleEnterPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            sendMessage();
            event.consume();
        }
    }

    /**
     * To start the dialog
     */
    public boolean start() {
        // try to connect to the server
        try {
            socket = new Socket(server, port);
        }
        // if it failed not much I can so
        catch(Exception ec) {
            display("Error connectiong to server:" + ec);
            return false;
        }

        String msg = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();
        display(msg);

        /* Creating both Data Stream */
        try
        {
            sInput  = new ObjectInputStream(socket.getInputStream());
            sOutput = new ObjectOutputStream(socket.getOutputStream());
        }
        catch (IOException eIO) {
            display("Exception creating new Input/output Streams: " + eIO);
            return false;
        }

        // creates the Thread to listen from the server
        new ListenFromServer().start();
        // Send our username to the server this is the only message that we
        // will send as a String. All other messages will be ChatMessage objects
        try
        {
            sOutput.writeObject(username);
        }
        catch (IOException eIO) {
            display("Exception doing login : " + eIO);
            disconnect();
            return false;
        }
        // success we inform the caller that it worked
        return true;
    }

    /*
     * To send a message to the console or the GUI
     */
    private void display(String msg) {
        txtAreaServerMsgs.appendText(msg + "\n"); // append to the ServerChatArea
    }

    /*
     * When something goes wrong
     * Close the Input/Output streams and disconnect not much to do in the catch clause
     */
    private void disconnect() {
        try {
            if(sInput != null) sInput.close();
        }
        catch(Exception e) {} // not much else I can do
        try {
            if(sOutput != null) sOutput.close();
        }
        catch(Exception e) {} // not much else I can do
        try{
            if(socket != null) socket.close();
        }
        catch(Exception e) {} // not much else I can do

        // inform the GUI
        connectionFailed();

    }

    public void connectionFailed() {
        btnLogin.setDisable(false);
        btnLogout.setDisable(true);
        // let the user change them
        txtHostIP.setEditable(true);
        // don't react to a <CR> after the username
        connected = false;
    }

    /*
     * a class that waits for the message from the server and append them to the JTextArea
     * if we have a GUI or simply System.out.println() it in console mode
     */
    class ListenFromServer extends Thread {

        public void run() {
            users =	FXCollections.observableArrayList();
            listUser.setItems(users);
            while(true) {
                try {
                    String msg = (String) sInput.readObject();
                    String[] split = msg.split(":");
                    if (split[1].equals("WHOISIN")) {
                        Platform.runLater(() -> {
                            users.add(split[0]);
                        });;
                    } else if (split[1].equals("REMOVE")) {
                        Platform.runLater(() -> {
                            users.remove(split[0]);
                        });
                    } else{
                        txtAreaServerMsgs.appendText(msg);
                    }
                }
                catch(IOException e) {
                    display("Server has close the connection");
                    connectionFailed();
                    Platform.runLater(() -> {
                        listUser.setItems(null);
                    });
                    break;
                }
                // can't happen with a String object but need the catch anyhow
                catch(ClassNotFoundException e2) {

                }
            }
        }
    }
}
