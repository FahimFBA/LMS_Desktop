module com.lms.lmsdesktop {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.lms.lmsdesktop to javafx.fxml;
    exports com.lms.lmsdesktop;
}