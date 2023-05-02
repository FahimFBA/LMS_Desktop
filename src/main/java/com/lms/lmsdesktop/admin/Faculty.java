package com.lms.lmsdesktop.admin;

public class Faculty {
    private String serial;
    private String name;
    private String email;
    private String password;
    private String courseSerial;
    private String courseCode;
    private String courseName;
    private String courseSection;

    public Faculty(String serial, String name, String email, String password, String courseSerial, String courseCode, String courseName, String courseSection) {
        this.serial = serial;
        this.name = name;
        this.email = email;
        this.password = password;
        this.courseSerial = courseSerial;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.courseSection = courseSection;
    }

    public String getSerial() {
        return serial;
    }
    public String getFacultyName() {
        return name; // Replace 'facultyName' with the correct variable name for the faculty name
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCourseSerial() {
        return courseSerial;
    }

    public void setCourseSerial(String courseSerial) {
        this.courseSerial = courseSerial;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseSection() {
        return courseSection;
    }

    public void setCourseSection(String courseSection) {
        this.courseSection = courseSection;
    }
}
