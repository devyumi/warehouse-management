package controller;

import domain.RoleType;
import domain.User;
import service.ExpenseService;
import service.ProductCategoryService;
import service.StockLogService;
import service.StockService;

import java.io.IOException;

public class StockController {

    private static final StockService stockService = new StockService();
    private static final ExpenseService expenseService = new ExpenseService();
    private static final StockLogService stockLogService = new StockLogService();
    private static final ProductCategoryService productCategoryService = new ProductCategoryService();

    public static void main(String[] args) throws IOException {
        User admin = User.builder()
                .id(1)
                .name("총 관리자")
                .roleType(RoleType.ADMIN)
                .build();

        User manager = User.builder()
                .id(2)
                .name("매니저")
                .roleType(RoleType.WAREHOUSE_MANAGER)
                .build();

        User businessMan = User.builder()
                .id(11)
                .name("사업가")
                .roleType(RoleType.BUSINESS_MAN)
                .build();


        stockLogService.findStockLogs();
    }
}
