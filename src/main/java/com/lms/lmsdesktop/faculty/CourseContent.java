package com.lms.lmsdesktop.faculty;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CourseContent {

    private StringProperty date;
    private StringProperty content;
    private BooleanProperty notification;
    private StringProperty notes;

    public CourseContent(String date, String content, boolean notification, String notes) {
        this.date = new SimpleStringProperty(date);
        this.content = new SimpleStringProperty(content);
        this.notification = new SimpleBooleanProperty(notification);
        this.notes = new SimpleStringProperty(notes);
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public StringProperty dateProperty() {
        return date;
    }

    public String getContent() {
        return content.get();
    }

    public void setContent(String content) {
        this.content.set(content);
    }

    public StringProperty contentProperty() {
        return content;
    }

    public boolean isNotification() {
        return notification.get();
    }

    public void setNotification(boolean notification) {
        this.notification.set(notification);
    }

    public BooleanProperty notificationProperty() {
        return notification;
    }

    public String getNotes() {
        return notes.get();
    }

    public void setNotes(String notes) {
        this.notes.set(notes);
    }

    public StringProperty notesProperty() {
        return notes;
    }
}
