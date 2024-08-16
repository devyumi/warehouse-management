package service;

import dao.StockDao;
import domain.ProductCategory;
import domain.User;
import dto.StockDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.List;

public class StockService {
    private static final StockDao stockDao = new StockDao();
    private static final ProductCategoryService productCategoryService = new ProductCategoryService();
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    /**
     * 전체 재고 조회
     * 총 관리자: 전체 재고 내역 조회
     * 창고 관리자: 관리하는 창고의 재고만 조회
     * 사업자: 자신의 재고만 조회
     *
     * @User: 총 관리자, 창고 관리자, 사업자
     */
    public void findStocks(User user) {
        switch (user.getRoleType().toString()) {

            case "ADMIN" -> {
                printStocks(stockDao.findAll());
            }

            case "WAREHOUSE_MANAGER" -> {
                printStocks(stockDao.findByManagerId(user.getId()));
            }

            case "BUSINESS_MAN" -> {
                printStocks(stockDao.findByBusinessManId(user.getId()));
            }
        }
    }

    /**
     * 카테고리별 재고 조회 (대, 중, 소분류)
     *
     * @User: 총 관리자, 창고 관리자, 사업자
     */
    public void findStocksByCategories() throws IOException {
        //대분류 선택
        System.out.println("대분류를 선택해주세요.");
        int mainCategoryId = createValidMainCategoryId();
        stockDao.findByParentId(mainCategoryId);

        //중분류 선택
        Loop:
        while (true) {
            System.out.println("1. 중분류 선택 | 2. 나가기");
            int input = Integer.parseInt(br.readLine());
            if (input == 1) {
                System.out.println("중분류를 선택해주세요.");
                int subCategoryId = createValidSubCategoryId(mainCategoryId);
                stockDao.findByParentId(subCategoryId);

                //소분류 선택
                while (true) {
                    System.out.println("1. 소분류 선택 | 2. 나가기");
                    input = Integer.parseInt(br.readLine());
                    if (input == 1) {
                        System.out.println("소분류를 선택해세요.");
                        stockDao.findByParentId(createValidSubCategoryId(subCategoryId));
                    } else if (input == 2) {
                        break Loop;
                    } else {
                        System.out.println("잘못된 번호입니다.");
                    }
                }
            } else if (input == 2) {
                break;
            } else {
                System.out.println("잘못된 번호입니다.");
            }
        }
    }

    private int createValidMainCategoryId() throws IOException {
        while (true) {
            List<ProductCategory> categories = productCategoryService.findMainCategories();
            printCategories(categories);
            System.out.print("번호 입력: ");

            int categoryId = Integer.parseInt(br.readLine());

            if (categories.stream()
                    .map(ProductCategory::getId)
                    .anyMatch(n -> n == categoryId)) {
                return categoryId;
            } else {
                System.out.println("잘못된 입력입니다.");
            }
        }
    }

    private int createValidSubCategoryId(Integer parentId) throws IOException {
        while (true) {
            List<ProductCategory> categories = productCategoryService.findSubCategories(parentId);
            printCategories(categories);
            System.out.print("번호 입력: ");

            int categoryId = Integer.parseInt(br.readLine());

            if (categories.stream()
                    .map(ProductCategory::getId)
                    .anyMatch(n -> n == categoryId)) {
                return categoryId;
            } else {
                System.out.println("잘못된 입력입니다.");
            }
        }
    }

    private void printStocks(List<StockDto> stocks) {
        System.out.print("\n\n" + "*".repeat(80) + " [재고 현황] " + "*".repeat(80) + "\n");
        System.out.println("-".repeat(170));
        System.out.printf("%-5s| %-10s | %-20s | %-20s | %-20s | %-20s | %-20s | %-20s | %-20s | %-20s | %-20s\n",
                "번호", "제품코드", "제품명", "창고위치", "재고위치", "원가", "수량", "제조일자", "유효기간", "제조사", "공급처명");
        System.out.println("-".repeat(170));

        for (StockDto stock : stocks) {
            System.out.printf("%-5d | %-10s | %-25s | %-20s | %-20s | %10s원 | %-10d | %d-%d-%d %d:%d:%d | %d-%d-%d %d:%d:%d | %-15s | %-15s\n",
                    stock.getId(), stock.getProductCode(), stock.getProductName(), stock.getVendorName(), stock.getSectionName(),
                    new DecimalFormat("###,###").format(stock.getCostPrice()), stock.getQuantity(),
                    stock.getManufacturedDate().getYear(), stock.getManufacturedDate().getMonthValue(), stock.getManufacturedDate().getDayOfMonth(),
                    stock.getManufacturedDate().getHour(), stock.getManufacturedDate().getMinute(), stock.getManufacturedDate().getSecond(),
                    stock.getExpirationDate().getYear(), stock.getExpirationDate().getMonthValue(), stock.getExpirationDate().getDayOfMonth(),
                    stock.getExpirationDate().getHour(), stock.getExpirationDate().getMinute(), stock.getExpirationDate().getSecond(),
                    stock.getManufacturer(), stock.getVendorName());
        }
    }

    private void printCategories(List<ProductCategory> categories) {
        System.out.print("\n\n" + "*".repeat(80) + " [분류 선택] " + "*".repeat(80) + "\n");
        System.out.println("-".repeat(170));
        System.out.printf("%-5s| %-10s\n",
                "번호", "분류명");
        System.out.println("-".repeat(170));

        for (ProductCategory category : categories) {
            System.out.printf("%-5d | %-10s\n",
                    category.getId(), category.getName());
        }
    }
}
