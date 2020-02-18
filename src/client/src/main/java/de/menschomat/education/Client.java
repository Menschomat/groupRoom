package de.menschomat.education;

import de.menschomat.education.Controller.MessageReader;
import de.menschomat.education.Controller.MessageWriter;
import de.menschomat.education.utils.ConfigReader;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * Hello world!
 */
public class Client {
    public static void main(String[] args) throws IOException {
        InetAddress ip = InetAddress.getByName(ConfigReader.getConfig().getProperty("hostname"));
        int port = Integer.parseInt(ConfigReader.getConfig().getProperty("port"));
        Scanner scn = new Scanner(System.in);
        try {
            Socket s = new Socket(ip, port);
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            Thread sendMessage = new Thread(new MessageWriter(scn, dos));
            Thread readMessage = new Thread(new MessageReader(dis));
            sendMessage.start();
            readMessage.start();
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }, "Shutdown-thread"));
        } catch (ConnectException e) {
            System.out.println("\033[0;31m" +"[ERROR] Client closed with error: " + e.getMessage());
        }
    }
}
