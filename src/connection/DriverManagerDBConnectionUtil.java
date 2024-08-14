package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionUtil {
    private static final String dbUrl = "jdbc:mysql://localhost:3306/warehouse_management?serverTimezone=Asia/Seoul&characterEncoding=UTF-8";
    private static final String dbUsername = "root";
    private static final String dbPassword = "0000";

    public static Connection getConnection() {
        try {
            Connection con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            con.setAutoCommit(false);
            return con;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
