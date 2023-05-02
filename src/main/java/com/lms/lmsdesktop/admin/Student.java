package com.lms.lmsdesktop.admin;

public class Student {
    private final String email;
    private final String name;
    private final String id;
    private final String password;
    private final String status;

    public Student(String email, String name, String id, String password, String status) {
        this.email = email;
        this.name = name;
        this.id = id;
        this.password = password;
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getStatus() {
        return status;
    }
}
