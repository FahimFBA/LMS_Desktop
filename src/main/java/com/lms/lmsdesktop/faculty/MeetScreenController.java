package com.lms.lmsdesktop.faculty;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;

import java.net.URI;

public class MeetScreenController {
    @FXML
    private JFXButton openLink;

    @FXML
    public void openBrowserMeet() {
        try {
            URI uri = new URI("https://meet.google.com/gkt-kxuw-qmd");
            java.awt.Desktop.getDesktop().browse(uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
