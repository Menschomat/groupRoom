package de.menschomat.education.Controller;

import model.ChatMessage;

import java.io.*;

public class MessageReader implements Runnable {
    private InputStream in;

    public MessageReader(InputStream in) {
        this.in = in;
    }

    @Override
    public void run() {
        while (true) {
            try {
                ChatMessage msg = (ChatMessage) new ObjectInputStream(in).readObject();
                System.out.println(String.format("[%s][%s] %s", msg.getTimestamp(), msg.getSender(), msg.getMessageBody()));
            } catch (Exception e) {
                System.out.println("\033[0;31m" + "[ERROR] Client closed with error");
                System.exit(1);
            }
        }
    }
}