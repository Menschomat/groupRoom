package de.menschomat.education;

import de.menschomat.education.Controller.MessageManager;
import de.menschomat.education.utils.ConfigReader;

import java.io.IOException;

public class Server {
    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(ConfigReader.getConfig().getProperty("port"));
        new MessageManager(port);

    }
}
