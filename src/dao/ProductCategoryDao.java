package dao;

import connection.DriverManagerDBConnectionUtil;
import domain.ProductCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDao {

    public List<ProductCategory> findByParentIdIsNull() {
        String query = new StringBuilder()
                .append("SELECT id, name ")
                .append("FROM product_category ")
                .append("WHERE parent_id IS NULL ").toString();

        List<ProductCategory> categories = new ArrayList<>();

        Connection con = null;
        try (PreparedStatement pstmt = con.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            con = DriverManagerDBConnectionUtil.getConnection();
            con.setReadOnly(true);

            while (rs.next()) {
                categories.add(ProductCategory.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .build());
            }
            return categories;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connectionClose(con);
        }
    }

    public List<ProductCategory> findByParentId(Integer parentId) {
        String query = new StringBuilder()
                .append("SELECT id, name ")
                .append("FROM product_category ")
                .append("WHERE parent_id = ? ").toString();

        List<ProductCategory> categories = new ArrayList<>();

        Connection con = null;
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            con = DriverManagerDBConnectionUtil.getConnection();
            con.setReadOnly(true);

            pstmt.setInt(1, parentId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    categories.add(ProductCategory.builder()
                            .id(rs.getInt("id"))
                            .name(rs.getString("name"))
                            .build());
                }
            }
            return categories;
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
