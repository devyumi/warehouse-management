package service;

import connection.DriverManagerDBConnectionUtil;
import dao.WarehouseDao;
import domain.Warehouse;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class WarehouseService {

    private static final WarehouseDao warehouseDao = new WarehouseDao();

    public List<Warehouse> findAllWarehouses() {
        Connection con = null;
        try {
            con = DriverManagerDBConnectionUtil.getInstance().getConnection();
            con.setReadOnly(true);

            return warehouseDao.findAll(con);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connectionClose(con);
        }
    }

    public Warehouse findWarehouseById(Integer managerId) {
        Connection con = null;
        try {
            con = DriverManagerDBConnectionUtil.getInstance().getConnection();
            con.setReadOnly(true);

            return warehouseDao.findOneByManagerId(con, managerId)
                    .orElseThrow(() -> new RuntimeException("관리하는 창고가 없습니다."));
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
