package service;

import dao.StockDao;
import domain.Stock;

import java.util.List;

public class StockService {
    private static final StockDao stockDao = new StockDao();

    /**
     * 전체 재고 조회
     *
     * @User: 총 관리자, 창고 관리자
     */
    public void findStocks() {
        printStock(stockDao.findAll());
    }

    /**
     * 전체 재고 조회
     *
     * @User: 사업자
     */
    public void findMyStocks() {
        /**
         * SELECT code, p.name AS pName, cost_price, FORMAT(width * height, 2) AS area, quantity, manufactured_date, expiration_date, manufacturer, v.name AS vName
         * FROM stock s
         * 	JOIN product p ON s.product_id = p.id
         *     JOIN vendor v ON p.vendor_id = v.id
         * WHERE business_man_id = 1
         * ORDER BY s.reg_date DESC;
         */
    }



    public void searchStockByLargeCategory() {

    }

    public void searchStockByMidCategory() {

    }

    public void searchStockBySmallCategory() {

    }

    public void searchStockByWarehouse() {

    }

    public void searchStockByVendor() {

    }

    private void printStock(List<Stock> stocks) {
        System.out.print("\n\n" + "*".repeat(80) + " [재고 현황] " + "*".repeat(80) + "\n");
        System.out.println("-".repeat(170));
        System.out.printf("%-5s| %-10s | %-25s | %10s | %10s | %10s | %-10s | %-10s | %-10s | %-10s\n",
                "순번", "제품코드", "제품명", "원가", "면적", "수량", "제조일자", "유효기간", "제조사", "공급처명");
        System.out.println("-".repeat(170));

        for (Stock stock : stocks) {
            System.out.printf("%-10s | %-25s | %10.0f원 | %10.2f㎡ | %-10d | %-20s | %-20s | %-15s | %-15s\n",
                    stock.getProduct().getCode(), stock.getProduct().getName(), stock.getProduct().getCostPrice(),
                    (stock.getHeight() * stock.getWidth()), stock.getQuantity(), stock.getManufacturedDate(), stock.getExpirationDate(),
                    stock.getProduct().getManufacturer(), stock.getProduct().getVendor().getName());
        }
    }
}
