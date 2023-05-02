package com.lms.lmsdesktop.admin;

import java.io.InputStream;
import java.sql.*;

import com.jfoenix.controls.JFXButton;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class ApprovedStudentsController {
    @FXML
    private JFXButton ExitButton;

    @FXML
    private JFXButton ExportButton;
    @FXML
    private JFXButton deleteButton;
    @FXML
    private JFXButton refreshButton;
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
                    if (status == 0) {
                        setStyle("-fx-background-color: #66ff66");
                    } else {
                        setStyle("-fx-background-color: #ff6666");
                    }
//                    setStyle(status == 1 ? "-fx-background-color: #ff6666" : "");
//                    setStyle(status == 0 ? "-fx-background-color: #66ff66" : "");
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

    @FXML
    public void ExitWindow() {
        ExitButton.getScene().getWindow().hide();
    }


    @FXML
    public void ChangeData() {
        try {

            // Load the FXML file as an input stream
            InputStream fxmlStream = getClass().getResourceAsStream("/com/lms/lmsdesktop/admin/ChangeStudentsData_Approved.fxml");

            // Create a new FXMLLoader instance and pass the input stream to the constructor
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(fxmlStream);



            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));

            // Get the controller instance and pass the selected student object to the initData method
            ChangeStudentsData_ApprovedController controller = fxmlLoader.getController();
            controller.initData(approvedStudentTable.getSelectionModel().getSelectedItem());

            // Show the window and do not block the code
            stage.show();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    public void refreshDB() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/student_signup", "root", "root")) {
            // Clear the current items in the table
            approvedStudentTable.getItems().clear();

            // Load the data from the database again
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM student_signup_table WHERE student_status = '0' OR student_status = '1'");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Student student = new Student(rs.getString("student_email"), rs.getString("student_name"), rs.getString("student_id"), rs.getString("student_password"), rs.getString("student_status"));
                approvedStudentTable.getItems().add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
