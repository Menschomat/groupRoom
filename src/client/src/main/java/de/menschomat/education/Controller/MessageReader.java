package de.menschomat.education.Controller;


import de.menschomat.education.utils.TimeUtils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MessageReader implements Runnable {
    private DataInputStream in;

    public MessageReader(DataInputStream in) {
        this.in = in;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String msg = new BufferedReader(
                        new InputStreamReader(in)
                ).readLine();
                System.out.println(String.format("[%s] %s", TimeUtils.getTimestamp(), msg));
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }
}