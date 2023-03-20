package com.lms.lmsdesktop.student_courseSelection;

import com.lms.lmsdesktop.MainApplication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CourseSelection extends Application{
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("student_courseSelection/course-selection-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Course Selection");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}