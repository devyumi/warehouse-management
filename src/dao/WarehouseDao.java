package dao;

import domain.Region;
import domain.User;
import domain.Warehouse;
import domain.WarehouseType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WarehouseDao {

    public List<Warehouse> findAll(Connection con) {
        String query = new StringBuilder()
                .append("SELECT * ")
                .append("FROM warehouse").toString();

        List<Warehouse> warehouses = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                warehouses.add(Warehouse.builder()
                        .id(rs.getInt("id"))
                        .user(User.builder()
                                .id(rs.getInt("manager_id"))
                                .build())
                        .warehouseType(WarehouseType.builder()
                                .id(rs.getInt("type_id"))
                                .build())
                        .code(rs.getString("code"))
                        .name(rs.getString("name"))
                        .region(Region.builder()
                                .id(rs.getInt("region_id"))
                                .build())
                        .detailAddress(rs.getString("detail_address"))
                        .contact(rs.getString("contact"))
                        .maxCapacity(rs.getDouble("max_capacity"))
                        .regDate(rs.getTimestamp("reg_date").toLocalDateTime())
                        .build());
            }
            return warehouses;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Warehouse> findOneByManagerId(Connection con, Integer managerId) {
        String query = new StringBuilder()
                .append("SELECT * ")
                .append("FROM warehouse ")
                .append("WHERE manager_id = ? ").toString();

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, managerId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs != null && rs.next()) {
                    return Optional.of(Warehouse.builder()
                            .id(rs.getInt("id"))
                            .user(User.builder()
                                    .id(rs.getInt("manager_id"))
                                    .build())
                            .warehouseType(WarehouseType.builder()
                                    .id(rs.getInt("type_id"))
                                    .build())
                            .code(rs.getString("code"))
                            .name(rs.getString("name"))
                            .region(Region.builder()
                                    .id(rs.getInt("region_id"))
                                    .build())
                            .detailAddress(rs.getString("detail_address"))
                            .contact(rs.getString("contact"))
                            .maxCapacity(rs.getDouble("max_capacity"))
                            .regDate(rs.getTimestamp("reg_date").toLocalDateTime())
                            .build());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }
}
