package de.menschomat.education.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MessageManager {
    Socket socket;

    public MessageManager(ConnectionHandler connectionHandler) throws IOException {
        socket = connectionHandler.getSocket();
    }

    public String readMsg() throws IOException {
        return new BufferedReader(
                new InputStreamReader(socket.getInputStream())
        ).readLine();
    }

    public void writeMsg(String string) throws IOException {
        new PrintWriter(
                socket.getOutputStream(), true
        ).println(string);
    }
}
