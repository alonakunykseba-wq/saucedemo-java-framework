package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationManager {
    private final static Properties properties;
    static {
        properties = new Properties();
        try (InputStream input = ConfigurationManager.class.getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(input);
        } catch (IOException e) {

            throw new RuntimeException("Failed to load config.properties file!", e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

}
