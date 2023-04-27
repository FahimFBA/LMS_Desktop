package com.lms.chatapp;

import java.io.IOException;
import java.io.PrintWriter;

public class ChatClientHandlerWriterAdapter extends ChatClientHandler {
    private PrintWriter writer;

    public ChatClientHandlerWriterAdapter(PrintWriter writer) {
        super(null, null);
        this.writer = writer;
    }

    @Override
    public void sendMessage(String message) throws IOException {
        writer.println(message);
    }
}
