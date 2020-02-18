package de.menschomat.education.Controller;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class MessageWriter implements Runnable {
    private DataOutputStream out;
    private Scanner scanner;

    public MessageWriter(Scanner scanner, DataOutputStream out) {
        this.out = out;
        this.scanner = scanner;
    }

    @Override
    public void run() {
        while (true) {
            new PrintWriter(out, true).println(scanner.nextLine());
        }
    }
}