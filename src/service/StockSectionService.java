package service;

import connection.DriverManagerDBConnectionUtil;
import dao.StockSectionDao;

import java.sql.Connection;
import java.sql.SQLException;

public class StockSectionService {
    private static final StockSectionDao stockSectionDao = new StockSectionDao();

    /**
     * 재고 위치 삭제
     * 재고 삭제 시 재고의 위치 정보가 함께 삭제된다.
     */
    public void deleteStockSection(Integer stockId) {
        Connection con = null;
        try {
            con = DriverManagerDBConnectionUtil.getInstance().getConnection();
            con.setAutoCommit(false);

            int result = stockSectionDao.deleteStockSection(con, stockId);

            if (result == 1) {
                con.commit();
            } else {
                con.rollback();
            }
        } catch (SQLException e) {
            transactionRollback(con);
        } finally {
            connectionClose(con);
        }
    }

    private void transactionRollback(Connection con) {
        try {
            if (con != null) {
                con.rollback();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
