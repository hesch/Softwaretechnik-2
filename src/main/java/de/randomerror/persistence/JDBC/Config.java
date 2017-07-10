package de.randomerror.persistence.JDBC;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 */
public class Config {
    private static Config instance = new Config();
    public static Config getInstance() {
        return instance;
    }

    private Config() {
        properties = new Properties();
        try {
            InputStream in = ClassLoader.getSystemResourceAsStream("jdbc.properties");
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Properties properties;

    private String getProperty(String name) {
        String out = properties.getProperty(name);
        return out == null ? "" : out;
    }

    public String getUrl() {
        return getProperty("jdbc.url");
    }

    public String getDatabase() {
        return getProperty("jdbc.database");
    }

    public String getUser() {
        return getProperty("jdbc.user");
    }

    public String getPassword() {
        return getProperty("jdbc.password");
    }
}
