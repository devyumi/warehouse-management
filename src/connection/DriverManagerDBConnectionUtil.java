package connection;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DriverManagerDBConnectionUtil {
    private static final DriverManagerDBConnectionUtil instance = new DriverManagerDBConnectionUtil();
    private static String dbUrl;
    private static String dbUsername;
    private static String dbPassword;

    private DriverManagerDBConnectionUtil() {
        Properties properties = new Properties();
        String propertiesFilePath = "resources/application.properties";
        try (InputStream input = new FileInputStream(propertiesFilePath)) {
            properties.load(input);
            dbUrl = properties.getProperty("database.url");
            dbUsername = properties.getProperty("database.username");
            dbPassword = properties.getProperty("database.password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static DriverManagerDBConnectionUtil getInstance() {
        return instance;
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }
}
