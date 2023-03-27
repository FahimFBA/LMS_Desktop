package com.lms.lmsdesktop.student;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignUpController {

    @FXML
    private Button SignMeUp;
    @FXML
    private TextField email;

    @FXML
    private TextField name;

    @FXML
    private TextField student_id;
    @FXML
    private PasswordField password;

    @FXML
    private PasswordField retype_password;


    @FXML
    void handleSignUp(ActionEvent event) {
        String student_email = email.getText();
        String student_name = name.getText();
        String student_id_string = student_id.getText();
        int student_id = Integer.parseInt(student_id_string);
        String student_password = password.getText();
        String student_retype_password = retype_password.getText();
        String sqlFilePath = "/resources/student_signup.sql";


        try {
            InputStream inputStream = getClass().getResourceAsStream(sqlFilePath);
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_signup", "root", "root");
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO signupinfo (student_email, student_name, student_id, student_password, student_password_recheck) VALUES (?, ?, ?, ?, ?)");
            stmt.setString(1, student_email);
            stmt.setString(2, student_name);
            stmt.setInt(3, student_id);
            stmt.setString(4, student_password);
            stmt.setString(5, student_retype_password);
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
