package com.lms.lmsdesktop.admin;

public class Student {
    private String email;
    private String name;
    private String id;
    private String password;
    private String status;

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

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
