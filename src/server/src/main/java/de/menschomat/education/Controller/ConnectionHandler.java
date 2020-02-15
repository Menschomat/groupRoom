package de.menschomat.education.Controller;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class ConnectionHandler {
    private int port = 8761;
    private ServerSocket serverSocket;

    public ConnectionHandler(int port) throws IOException {
        this.port = port;
        serverSocket = new ServerSocket(this.port);
    }

    public Socket getSocket() throws IOException {
        return serverSocket.accept();
    }
}
