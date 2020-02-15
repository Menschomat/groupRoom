package de.menschomat.education.utils;

import java.io.IOException;
import java.util.Properties;

public abstract class ConfigReader {
    public static Properties getConfig() throws IOException {
        Properties prop = new Properties();
        prop.load(ConfigReader.class.getClassLoader().getResourceAsStream("server.config"));
        return prop;
    }
}
