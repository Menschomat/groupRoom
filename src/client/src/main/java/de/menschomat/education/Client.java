package de.menschomat.education;

import de.menschomat.education.Controller.ConnectionHandler;
import de.menschomat.education.Controller.MessageManager;
import de.menschomat.education.utils.ConfigReader;

import java.io.IOException;

/**
 * Hello world!
 */
public class Client {
    public static void main(String[] args) throws IOException {
        String hostname = ConfigReader.getConfig().getProperty("hostname");
        int port = Integer.parseInt(ConfigReader.getConfig().getProperty("port"));
        MessageManager messageManager = new MessageManager(new ConnectionHandler(port, hostname));
        while (true) {
            messageManager.writeMsg("fdsfd");
        }

    }
}
