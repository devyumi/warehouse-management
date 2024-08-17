package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StockSectionDao {
    public int deleteStockSection(Connection con, Integer stockId) {
        String query = new StringBuilder()
                .append("DELETE FROM stock_section ")
                .append("WHERE stock_id = ? ").toString();

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, stockId);

            if (pstmt.executeUpdate() == 1) {
                con.commit();
                System.out.println("재고 위치 정보가 삭제되었습니다.");
                return 1;
            } else {
                throw new RuntimeException("해당 재고의 위치 정보를 삭제할 수 없습니다.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
