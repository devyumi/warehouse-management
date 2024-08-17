package service;

import connection.DriverManagerDBConnectionUtil;
import dao.WarehouseDao;
import domain.User;
import domain.Warehouse;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class WarehouseService {

    private static final WarehouseDao warehouseDao = new WarehouseDao();

    public List<Warehouse> findWarehouses() {
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

    public Warehouse findWarehouseByManagerId(User user) {
        Connection con = null;
        try {
            con = DriverManagerDBConnectionUtil.getInstance().getConnection();
            con.setReadOnly(true);

            return warehouseDao.findOneByManagerId(con, user.getId())
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

    public void printWarehouses(List<Warehouse> warehouses) {
        System.out.println("\n\n[창고 현황]");
        System.out.println("-".repeat(150));
        System.out.printf("%-3s| %-5s | %-10s\n",
                "번호", "창고코드", "창고명");
        System.out.println("-".repeat(150));

        for (Warehouse warehouse : warehouses) {
            System.out.printf("%-5d%-10s%-10s\n",
                    warehouse.getId(), warehouse.getCode(), warehouse.getName());
        }
    }
}
