package de.menschomat.education.Controller;

import model.ChatMessage;
import utils.JsonUtil;
import utils.TimeUtils;

import java.io.*;
import java.util.Scanner;

public class MessageWriter implements Runnable {
    private OutputStream out;
    private Scanner scanner;

    public MessageWriter(Scanner scanner, OutputStream out) {
        this.out = out;
        this.scanner = scanner;
    }

    @Override
    public void run() {
        while (true) {
            try {
                new ObjectOutputStream(out).writeObject(new ChatMessage(
                        "",
                        "all",
                        TimeUtils.getShortTimestamp(),
                        scanner.nextLine()
                ));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}