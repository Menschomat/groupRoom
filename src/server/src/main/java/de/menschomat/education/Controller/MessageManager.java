package de.menschomat.education.Controller;

import java.io.*;
import java.net.Socket;

public class MessageManager {
    public MessageManager(ConnectionHandler connectionHandler) throws IOException {
        while (true) {
            Socket socket = connectionHandler.getSocket();
            while (socket.isConnected()) {
                System.out.println(readMsg(socket));
            }
        }
    }

    private String readMsg(Socket socket) throws IOException {
        return new BufferedReader(
                new InputStreamReader(socket.getInputStream())
        ).readLine();
    }

    private void writeMsg(Socket socket, String string) throws IOException {
        new PrintWriter(
                socket.getOutputStream(), true
        ).println(string);
    }
}
