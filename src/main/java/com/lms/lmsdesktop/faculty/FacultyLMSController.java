package com.lms.lmsdesktop.faculty;

import com.jfoenix.controls.JFXButton;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FacultyLMSController implements Initializable {

    @FXML
    private AnchorPane slider;
    @FXML
    private JFXButton facultyClassroom;

    @FXML
    private Label Menu;

    @FXML
    private Label MenuClose;

    @FXML
    private JFXButton calculator;

    @FXML
    private JFXButton calculatorFromSlider;
    @FXML
    private JFXButton Meet;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        slider.setTranslateX(-176);
        Menu.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(0);
            slide.play();

            slider.setTranslateX(-176);

            slide.setOnFinished((ActionEvent e)-> {
                Menu.setVisible(false);
                MenuClose.setVisible(true);
            });
        });

        MenuClose.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(-176);
            slide.play();

            slider.setTranslateX(0);

            slide.setOnFinished((ActionEvent e)-> {
                Menu.setVisible(true);
                MenuClose.setVisible(false);
            });
        });
    }

    @FXML
    void openClassroom(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("faculty-classroom-view.fxml"));
            Parent root = loader.load();
            FacultyClassroomController facultyClassroomController = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            // hide the main window
            Stage stage1 = (Stage) facultyClassroom.getScene().getWindow();
            stage1.hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void openCalculator(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/lms/lmsdesktop/calculator/calculator-view.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openMeetWindow(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/lms/lmsdesktop/faculty/google-meet-view.fxml"));
            Parent googleMeetView = fxmlLoader.load();
            Scene scene = new Scene(googleMeetView);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Google Meet");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
