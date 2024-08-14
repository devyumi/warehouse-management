package service;

import dao.WarehouseDao;
import domain.User;
import domain.Warehouse;
import exception.ErrorMessage;
import exception.WarehouseException;

public class WarehouseService {

    private static final WarehouseDao warehouseDao = new WarehouseDao();

    public Warehouse findOne(User user) {
        return warehouseDao.findOneByManagerId(user.getId())
                .orElseThrow(() -> new WarehouseException(ErrorMessage.FIND_WAREHOUSE_FAIL));
    }
}
