package com.lms.lmsdesktop.faculty;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewDataAddController {
    @FXML
    private TextField course_content;
    @FXML
    private JFXButton ExitButton;
    @FXML
    private JFXButton Exit;

    @FXML
    private DatePicker date;

    @FXML
    private JFXButton filePicker;

    @FXML
    private TextField notes;

    @FXML
    private JFXCheckBox notification_checkbox;


    @FXML
    void ExitWindow(ActionEvent event) {
        Stage stage = (Stage) Exit.getScene().getWindow();
        stage.close();
    }

}
