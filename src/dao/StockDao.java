package dao;

import connection.DriverManagerDBConnectionUtil;
import dto.StockDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockDao {

    public List<StockDto> findAll() {
        String query = new StringBuilder()
                .append("SELECT s.id AS sId, p.code AS pCode, p.name AS pName, w.name AS wName, ss.name AS ssName, cost_price, quantity, manufactured_date, expiration_date, manufacturer, v.name AS vName ")
                .append("FROM stock s ")
                .append("JOIN product p ON s.product_id = p.id ")
                .append("JOIN vendor v ON p.vendor_id = v.id ")
                .append("JOIN stock_section ss ON s.id = ss.id ")
                .append("JOIN warehouse w ON ss.warehouse_id = w.id ")
                .append("ORDER BY s.reg_date DESC ").toString();

        List<StockDto> stocks = new ArrayList<>();

        Connection con = null;
        try (PreparedStatement pstmt = con.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            con = DriverManagerDBConnectionUtil.getConnection();
            con.setReadOnly(true);

            while (rs.next()) {
                stocks.add(StockDto.builder()
                        .id(rs.getInt("sId"))
                        .productCode(rs.getString("pName"))
                        .productName(rs.getString("pName"))
                        .warehouseName(rs.getString("wName"))
                        .sectionName(rs.getString("ssName"))
                        .costPrice(rs.getDouble("cost_price"))
                        .quantity(rs.getInt("quantity"))
                        .manufacturedDate(rs.getTimestamp("manufactured_date").toLocalDateTime())
                        .expirationDate(rs.getTimestamp("expiration_date").toLocalDateTime())
                        .manufacturer(rs.getString("manufacturer"))
                        .vendorName(rs.getString("vendor_name"))
                        .build());
            }
            return stocks;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connectionClose(con);
        }
    }

    public List<StockDto> findByManagerId(Integer userId) {
        String query = new StringBuilder()
                .append("SELECT s.id AS sId, p.code AS pCode, p.name AS pName, w.name AS wName, ss.name AS ssName, cost_price, quantity, manufactured_date, expiration_date, manufacturer, v.name AS vName ")
                .append("FROM stock s ")
                .append("JOIN product p ON s.product_id = p.id ")
                .append("JOIN vendor v ON p.vendor_id = v.id ")
                .append("JOIN stock_section ss ON s.id = ss.id ")
                .append("JOIN warehouse w ON ss.warehouse_id = w.id ")
                .append("WHERE manager_id = ? ")
                .append("ORDER BY s.reg_date DESC ").toString();

        List<StockDto> stocks = new ArrayList<>();

        Connection con = null;
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            con = DriverManagerDBConnectionUtil.getConnection();
            con.setReadOnly(true);

            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    stocks.add(StockDto.builder()
                            .id(rs.getInt("sId"))
                            .productCode(rs.getString("pName"))
                            .productName(rs.getString("pName"))
                            .warehouseName(rs.getString("wName"))
                            .sectionName(rs.getString("ssName"))
                            .costPrice(rs.getDouble("cost_price"))
                            .quantity(rs.getInt("quantity"))
                            .manufacturedDate(rs.getTimestamp("manufactured_date").toLocalDateTime())
                            .expirationDate(rs.getTimestamp("expiration_date").toLocalDateTime())
                            .manufacturer(rs.getString("manufacturer"))
                            .vendorName(rs.getString("vendor_name"))
                            .build());
                }
            }
            return stocks;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connectionClose(con);
        }
    }

    public List<StockDto> findByBusinessManId(Integer userId) {
        String query = new StringBuilder()
                .append("SELECT s.id AS sId, p.code AS pCode, p.name AS pName, w.name AS wName, ss.name AS ssName, cost_price, quantity, manufactured_date, expiration_date, manufacturer, v.name AS vName ")
                .append("FROM stock s ")
                .append("JOIN product p ON s.product_id = p.id ")
                .append("JOIN vendor v ON p.vendor_id = v.id ")
                .append("JOIN stock_section ss ON s.id = ss.id ")
                .append("JOIN warehouse w ON ss.warehouse_id = w.id ")
                .append("WHERE business_man_id = ? ")
                .append("ORDER BY s.reg_date DESC ").toString();

        List<StockDto> stocks = new ArrayList<>();

        Connection con = null;
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            con = DriverManagerDBConnectionUtil.getConnection();
            con.setReadOnly(true);

            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    stocks.add(StockDto.builder()
                            .id(rs.getInt("sId"))
                            .productCode(rs.getString("pName"))
                            .productName(rs.getString("pName"))
                            .warehouseName(rs.getString("wName"))
                            .sectionName(rs.getString("ssName"))
                            .costPrice(rs.getDouble("cost_price"))
                            .quantity(rs.getInt("quantity"))
                            .manufacturedDate(rs.getTimestamp("manufactured_date").toLocalDateTime())
                            .expirationDate(rs.getTimestamp("expiration_date").toLocalDateTime())
                            .manufacturer(rs.getString("manufacturer"))
                            .vendorName(rs.getString("vendor_name"))
                            .build());
                }
            }
            return stocks;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connectionClose(con);
        }
    }

    public List<StockDto> findByParentId(Integer categoryId) {
        String query = new StringBuilder()
                .append("SELECT s.id AS sId, p.code AS pCode, p.name AS pName, w.name AS wName, ss.name AS ssName, cost_price, quantity, manufactured_date, expiration_date, manufacturer, v.name AS vName ")
                .append("FROM stock s ")
                .append("JOIN product p ON s.product_id = p.id ")
                .append("JOIN vendor v ON p.vendor_id = v.id ")
                .append("JOIN stock_section ss ON s.id = ss.id ")
                .append("JOIN warehouse w ON ss.warehouse_id = w.id ")
                .append("JOIN product_category pc ON p.category_id = pc.id ")
                .append("WHERE pc.id = ? OR pc.parent_id = ? OR pc.parent_id IN ( ")
                .append("SELECT id FROM product_category WHERE parent_id = ? ) ")
                .append("ORDER BY s.reg_date DESC ").toString();

        List<StockDto> stocks = new ArrayList<>();

        Connection con = null;
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            con = DriverManagerDBConnectionUtil.getConnection();
            con.setReadOnly(true);

            pstmt.setInt(1, categoryId);
            pstmt.setInt(2, categoryId);
            pstmt.setInt(3, categoryId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    stocks.add(StockDto.builder()
                            .id(rs.getInt("sId"))
                            .productCode(rs.getString("pName"))
                            .productName(rs.getString("pName"))
                            .warehouseName(rs.getString("wName"))
                            .sectionName(rs.getString("ssName"))
                            .costPrice(rs.getDouble("cost_price"))
                            .quantity(rs.getInt("quantity"))
                            .manufacturedDate(rs.getTimestamp("manufactured_date").toLocalDateTime())
                            .expirationDate(rs.getTimestamp("expiration_date").toLocalDateTime())
                            .manufacturer(rs.getString("manufacturer"))
                            .vendorName(rs.getString("vendor_name"))
                            .build());
                }
            }
            return stocks;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connectionClose(con);
        }
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
