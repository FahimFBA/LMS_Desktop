module com.lms.lmsdesktop {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.lms.lmsdesktop to javafx.fxml;
    exports com.lms.lmsdesktop;
    opens com.lms.lmsdesktop.student to javafx.fxml;
    exports com.lms.lmsdesktop.student;
    opens com.lms.lmsdesktop.faculty to javafx.fxml;
    exports com.lms.lmsdesktop.faculty;
    opens com.lms.lmsdesktop.admin to javafx.fxml;
    exports com.lms.lmsdesktop.admin;

}