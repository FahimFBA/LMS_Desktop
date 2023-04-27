package com.lms.lmsdesktop.chat;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ChatMessage implements Serializable {
    public enum MessageType {
        CONNECT,
        DISCONNECT,
        MESSAGE
    }
    private String sender;
    private String receiver;
    private String message;
    private LocalDateTime timestamp;

    public ChatMessage(String sender, String receiver, MessageType message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = String.valueOf(message);
        this.timestamp = LocalDateTime.now();
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "[" + timestamp.toString() + "] " + sender + " to " + receiver + ": " + message;
    }
}
