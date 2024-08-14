package dao;

import connection.DriverManagerDBConnectionUtil;
import domain.Region;
import domain.User;
import domain.Warehouse;
import domain.WarehouseType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class WarehouseDao {

    public Optional<Warehouse> findOneByManagerId(Integer managerId) {
        String query = new StringBuilder()
                .append("SELECT * ")
                .append("FROM warehouse ")
                .append("WHERE manager_id = ? ").toString();

        Connection con = null;
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            con = DriverManagerDBConnectionUtil.getInstance().getConnection();
            con.setReadOnly(true);
            pstmt.setInt(1, managerId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs != null && rs.next()) {
                    return Optional.of(Warehouse.builder()
                            .id(rs.getInt(1))
                            .user(rs.getObject(2, User.class))
                            .warehouseType(rs.getObject(3, WarehouseType.class))
                            .name(rs.getString(4))
                            .region(rs.getObject(5, Region.class))
                            .detailAddress(rs.getString(6))
                            .contact(rs.getString(7))
                            .maxCapacity(rs.getDouble(8))
                            .regDate(rs.getTimestamp(9).toLocalDateTime())
                            .modDate(rs.getTimestamp(10).toLocalDateTime())
                            .build());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connectionClose(con);
        }
        return Optional.empty();
    }

    private void connectionClose(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
