package com.lms.lmsdesktop.admin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.jfoenix.controls.JFXButton;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ApprovedStudentsController {
    @FXML
    private JFXButton ExitButton;

    @FXML
    private JFXButton ExportButton;

    @FXML
    private TableView<Student> approvedStudentTable;

    @FXML
    private TableColumn<Student, String> studentPassword;

    @FXML
    private TableColumn<Student, String> student_Email;

    @FXML
    private TableColumn<Student, String> student_ID;

    @FXML
    private TableColumn<Student, String> student_Name;

    @FXML
    private TableColumn<Student, String> student_Status;

    @FXML
    private JFXButton updateButton;

    private ObservableList<Student> studentData = FXCollections.observableArrayList();

    public void initialize() {
        // Set up table columns
        student_Email.setCellValueFactory(new PropertyValueFactory<>("email"));
        student_Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        student_ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        studentPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        student_Status.setCellValueFactory(new PropertyValueFactory<>("status"));
        student_Status.setCellFactory(col -> new TableCell<Student, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    int status = Integer.parseInt(item);
                    setText(status == 0 ? "OK" : "BLOCKED");
                    setStyle(status == 1 ? "-fx-background-color: #ff6666" : "");
                }
            }
        });

        // Populate table data from database
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/student_signup", "root", "root");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM student_signup_table WHERE student_status IN ('0', '1')")) {

            while (rs.next()) {
                Student student = new Student(rs.getString("student_email"), rs.getString("student_name"), rs.getString("student_id"), rs.getString("student_password"), rs.getString("student_status"));
                studentData.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Set table data
        approvedStudentTable.setItems(studentData);
    }
}
