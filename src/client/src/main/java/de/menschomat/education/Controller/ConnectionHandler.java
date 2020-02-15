package de.menschomat.education.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConnectionHandler {
    private Socket socket;

    public ConnectionHandler(int port, String host) throws IOException {
        socket = new Socket(host, port);
    }

    public Socket getSocket() {
        return socket;
    }
}
