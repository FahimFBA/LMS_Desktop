package com.lms.lmsdesktop.chat;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class ServerController {
    @FXML
    private TextArea txtAreaChatMsg;
    @FXML
    private TextArea txtAreaEventLog;
    @FXML
    private ListView<String> listUsersConnected;
    @FXML
    private Button btnStartServer;
    @FXML
    private Button btnStopServer;

    public Server server;

    @FXML
    private Button exportEvent;

    private ObservableList<String> users;

    public void startServer() {
        // create a new Server
        server = new Server(1500, this);
        users = FXCollections.observableArrayList();
        listUsersConnected.setItems(users);
        new ServerRunning().start();
        btnStartServer.setDisable(true);
        btnStopServer.setDisable(false);
    }

    public void stopServer() {
        if(server != null) {
            server.stop();
            btnStopServer.setDisable(true);
            btnStartServer.setDisable(false);
            listUsersConnected.setItems(null);
            server = null;
            return;
        }
    }

    /*
     * A thread to run the Server
     */
    class ServerRunning extends Thread {
        public void run() {
            server.start();         // should execute until if fails
            // the server failed
            appendEvent("Server Stopped\n");
            server = null;
            users = null;
        }
    }

    public void addUser(String user) {
        Platform.runLater(() -> {
            users.add(user);
        });
    }
    public void appendEvent(String string) {
        txtAreaEventLog.appendText(string);
    }

    public void appendRoom(String messageLf) {
        txtAreaChatMsg.appendText(messageLf);
    }

    public void remove(String username) {
        Platform.runLater(() -> {
            users.remove(username);
        });
    }

    public void exportEventLogExcel() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("EventLogs");
        String[] eventLogs = txtAreaEventLog.getText().split("\n");

        for (int i = 0; i < eventLogs.length; i++) {
            Row row = sheet.createRow(i);
            Cell cell = row.createCell(0);
            cell.setCellValue(eventLogs[i]);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        String fileName = "eventLogs_" + LocalDateTime.now().format(formatter) + ".xlsx";

        try {
            Path eventLogsPath = Paths.get("eventLogs");
            Files.createDirectories(eventLogsPath);
            File file = new File(eventLogsPath.toFile(), fileName);
            FileOutputStream outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
            outputStream.close();
            workbook.close();

            appendEvent("Event log exported to: " + file.getAbsolutePath() + "\n");
        } catch (IOException e) {
            appendEvent("Error exporting event log: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }
}
