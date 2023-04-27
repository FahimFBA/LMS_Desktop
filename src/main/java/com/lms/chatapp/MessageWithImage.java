package com.lms.chatapp;

import javafx.scene.image.Image;

public class MessageWithImage {
    private String message;
    private Image image;

    public MessageWithImage(String message, Image image) {
        this.message = message;
        this.image = image;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}