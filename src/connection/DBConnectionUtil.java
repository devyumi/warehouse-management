package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionUtil implements AutoCloseable {
    private final String dbUrl = "jdbc:mysql://localhost:3306/warehouse_management?serverTimezone=Asia/Seoul&characterEncoding=UTF-8";
    private final String dbUsername = "root";
    private final String dbPassword = "0000";

    public Connection getConnection() {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)) {
            connection.setAutoCommit(false);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void close() {
        try {
            System.out.println("연결 해제");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
