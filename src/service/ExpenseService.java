package service;

import dao.ExpenseDao;
import domain.Expense;
import domain.User;
import dto.ExpenseEditDto;
import dto.ExpenseSaveDto;
import dto.ProfitDto;
import dto.TotalAssetDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;

public class ExpenseService {
    private static final ExpenseDao expenseDao = new ExpenseDao();
    private static final WarehouseService warehouseService = new WarehouseService();
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    /**
     * 지출 조회
     * 총 관리자: 전체 지출 내역 조회
     * 창고 관리자: 관리하는 창고의 지출만 조회
     *
     * @User: 총 관리자, 창고 관리자
     */
    public void findExpenses(User user) {
        switch (user.getRoleType().toString()) {

            case "ADMIN" -> {
                printExpenses(expenseDao.findAll());
            }

            case "WAREHOUSE_MANAGER" -> {
                printExpenses(expenseDao.findById(user.getId()));
            }
        }
    }

    /**
     * 지출 조회 (필터링: 지출 구분)
     *
     * @User: 총 관리자, 창고 관리자
     */
    public void findExpensesByCategory(User user) throws IOException {
        switch (user.getRoleType().toString()) {

            case "ADMIN" -> {
                printExpenses(expenseDao.findAllByCategory(createValidCategoryId()));
            }

            case "WAREHOUSE_MANAGER" -> {
                printExpenses(expenseDao.findByCategory(user.getId(), createValidCategoryId()));
            }
        }

    }

    /**
     * 연간 지출 내역 조회
     *
     * @User: 총 관리자, 창고 관리자
     */
    public void findExpensesByYear(User user) throws IOException {
        switch (user.getRoleType().toString()) {

            case "ADMIN" -> {
                printExpenses(expenseDao.findAllByYear(createValidYear()));
            }

            case "WAREHOUSE_MANAGER" -> {
                printExpenses(expenseDao.findByYear(user.getId(), createValidYear()));
            }
        }
    }

    /**
     * 매출 내역 조회
     *
     * @User: 총 관리자, 창고 관리자
     */
    public void findProfit(User user) {
        switch (user.getRoleType().toString()) {

            case "ADMIN" -> {
                printProfits(expenseDao.findProfits());
            }

            case "WAREHOUSE_MANAGER" -> {
                printProfits(expenseDao.findProfitById(user.getId()));
            }
        }
    }

    /**
     * 총정산 내역 조회 (지출계, 매출계, 순수익, 순수익 증감률)
     *
     * @User: 총 관리자
     */
    public void findTotalAsset() {
        printTotalAsset(expenseDao.findTotalAsset());
    }

    /**
     * 지출 등록
     *
     * @param user
     * @throws IOException
     * @User: 총 관리자, 창고 관리자
     */
    public void saveExpense(User user) throws IOException {
        expenseDao.saveExpense(
                ExpenseSaveDto.builder()
                        .warehouseId(warehouseService.findOne(user).getId())
                        .expenseDate(createValidDate())
                        .categoryId(createValidCategoryId())
                        .expenseAmount(createValidAmount())
                        .description(createValidDescription())
                        .paymentMethod(createValidPaymentMethod())
                        .build());
    }

    /**
     * 지출 수정
     * 총 관리자: 모든 지출 내역 수정 가능
     * 창고 관리자: 자신이 등록한 지출 내역만 수정 가능
     *
     * @param user
     * @User: 총 관리자, 창고 관리자
     */
    public void updateExpense(User user) throws IOException {
        switch (user.getRoleType().toString()) {

            case "ADMIN" -> {
                expenseDao.updateExpense(ExpenseEditDto.builder()
                        .id(findValidExpenseId(expenseDao.findAll()))
                        .expenseDate(createValidDate())
                        .categoryId(createValidCategoryId())
                        .expenseAmount(createValidAmount())
                        .description(createValidDescription())
                        .paymentMethod(createValidPaymentMethod())
                        .build());
            }

            case "WAREHOUSE_MANAGER" -> {
                expenseDao.updateExpense(ExpenseEditDto.builder()
                        .id(findValidExpenseId(expenseDao.findById(user.getId())))
                        .expenseDate(createValidDate())
                        .categoryId(createValidCategoryId())
                        .expenseAmount(createValidAmount())
                        .description(createValidDescription())
                        .paymentMethod(createValidPaymentMethod())
                        .build());
            }
        }
    }

    /**
     * 지출 삭제
     * 총 관리자: 모든 지출 내역 삭제 가능
     * 창고 관리자: 자신이 등록한 지출 내역만 삭제 가능
     *
     * @param user
     * @User: 총 관리자, 창고 관리자
     */
    public void deleteExpense(User user) throws IOException {
        switch (user.getRoleType().toString()) {

            case "ADMIN" -> {
                expenseDao.deleteExpense(findValidExpenseId(expenseDao.findAll()));
            }

            case "WAREHOUSE_MANAGER" -> {
                expenseDao.deleteExpense(findValidExpenseId(expenseDao.findById(user.getId())));
            }
        }
    }

    private LocalDate createValidDate() throws IOException {
        int year;
        int month;
        int day;

        while (true) {
            System.out.println("지출일을 입력합니다.\n");
            System.out.print("연도 입력 (2023~2024): ");
            year = Integer.parseInt(br.readLine());

            if (year != 2023 && year != 2024) {
                System.out.println("\n ** 잘못된 연도 입니다. **\n");
                continue;
            }

            System.out.print("월 입력 (1~12): ");
            month = Integer.parseInt(br.readLine());

            if (month < 1 || month > 12) {
                System.out.println("\n ** 잘못된 월입니다. **\n");
                continue;
            } else if (month == 2) {
                System.out.print("일 입력 (1~28): ");
                day = Integer.parseInt(br.readLine());

                if (day < 1 || day > 28) {
                    System.out.println("\n ** 잘못된 일입니다. **\n");
                    continue;
                }
            } else if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                System.out.print("일 입력 (1~31): ");
                day = Integer.parseInt(br.readLine());

                if (day < 1 || day > 31) {
                    System.out.println("\n ** 잘못된 일입니다. **\n");
                    continue;
                }
            } else {
                System.out.print("일 입력 (1~31): ");
                day = Integer.parseInt(br.readLine());

                if (day < 1 || day > 30) {
                    System.out.println("\n ** 잘못된 일입니다. **\n");
                    continue;
                }
            }
            day = Integer.parseInt(br.readLine());
            return LocalDate.of(year, month, day);
        }
    }

    private int createValidCategoryId() throws IOException {
        int categoryId;

        while (true) {
            System.out.println("지출 구분을 선택하세요.");
            System.out.println("1. 유지보수비 | 2. 인건비 | 3. 운송비");
            categoryId = Integer.parseInt(br.readLine());

            if (categoryId < 1 || categoryId > 3) {
                System.out.println("다시 선택해주세요.");
                continue;
            }
            return categoryId;
        }
    }

    private double createValidAmount() throws IOException {
        double amount;

        while (true) {
            System.out.println("가격을 입력하세요.");
            amount = Double.parseDouble(br.readLine());

            if (amount < 0) {
                System.out.println("0원 이하는 입력할 수 없습니다.");
                continue;
            } else if (isNotAmount(String.valueOf(amount))) {
                System.out.println("문자, 특수문자, 공백은 입력할 수 없습니다.");
                continue;
            }
            return amount;
        }
    }

    private String createValidDescription() throws IOException {
        int description;

        while (true) {
            System.out.println("지출에 대한 설명을 입력하시겠습니까?");
            System.out.println("1. 예 | 2. 아니오");
            description = Integer.parseInt(br.readLine());

            if (description == 2) {
                return "";
            } else if (description == 1) {
                System.out.println("지출에 대한 설명을 입력해주세요.");
                return br.readLine();
            } else {
                System.out.println("잘못된 번호입니다.");
            }
        }
    }

    private String createValidPaymentMethod() throws IOException {
        int paymentMethod;

        while (true) {
            System.out.println("지출 방법을 선택해세요.");
            System.out.println("1. 카드 | 2. 계좌이체");
            paymentMethod = Integer.parseInt(br.readLine());

            if (paymentMethod == 1) {
                return "카드";
            } else if (paymentMethod == 2) {
                return "계좌이체";
            } else {
                System.out.println("잘못된 번호입니다.");
            }
        }
    }

    /**
     * 문자, 공백, 특수문자가 포함되어 있는지 확인
     *
     * @param input String
     * @return boolean
     */
    private boolean isNotAmount(String input) {
        return input.matches("^[a-zA-Z가-힣]*$") ||
                input.matches("^[!@#$%^&*]*$") ||
                input.matches("^[\\t\\n\\f\\r]*$");
    }

    private int findValidExpenseId(List<Expense> expenses) throws IOException {
        while (true) {
            System.out.println("수정할 지출 내역의 번호를 선택하세요.");
            System.out.print("번호 입력: ");
            int expenseId = Integer.parseInt(br.readLine());

            if (expenses.stream()
                    .map(Expense::getId)
                    .anyMatch(n -> n == expenseId)) {
                return expenseId;
            } else {
                System.out.println("조회할 수 없는 지출 건입니다.");
            }
        }
    }

    private int createValidYear() throws IOException {
        int year;

        while (true) {
            System.out.println("조회할 연도를 입력하세요.");
            System.out.print("연도: ");
            year = Integer.parseInt(br.readLine());

            if (year >= 2023 && year <= 2024) {
                return year;
            } else {
                System.out.println("조회할 수 없는 숫자입니다.");
            }
        }
    }

    private void printExpenses(List<Expense> expenses) {
        System.out.print("\n\n" + "*".repeat(80) + " [지출 현황] " + "*".repeat(80) + "\n");
        System.out.println("-".repeat(170));
        System.out.printf("%-5s| %-10s | %-20s | %10s | %10s | %50s | %-10s\n",
                "번호", "창고명", "지출일", "지출구분", "지출금액", "설명", "지출방법");
        System.out.println("-".repeat(170));

        for (Expense expense : expenses) {
            System.out.printf("%-5d | %-10s | %d-%d-%d | %-10s | %-10.0f원 | %-50s | %-10s\n",
                    expense.getId(), expense.getWarehouse().getName(),
                    expense.getExpenseDate().getYear(), expense.getExpenseDate().getMonthValue(), expense.getExpenseDate().getDayOfMonth(),
                    expense.getCategory().getName(), expense.getExpenseAmount(), expense.getDescription(), expense.getPayment_method());
        }
    }

    private void printProfits(List<ProfitDto> profits) {
        System.out.print("\n\n" + "*".repeat(80) + " [매출 현황] " + "*".repeat(80) + "\n");
        System.out.println("-".repeat(170));
        System.out.printf("%-5s| %-10s | %-20s | %20s\n",
                "번호", "창고명", "계약일", "매출금액");
        System.out.println("-".repeat(170));

        for (ProfitDto profit : profits) {
            System.out.printf("%-5d | %-10s | %d-%d-%d | %-10s원\n",
                    profit.getId(), profit.getName(),
                    profit.getContractDate().getYear(), profit.getContractDate().getMonthValue(), profit.getContractDate().getDayOfMonth(),
                    new DecimalFormat("###,###").format(profit.getProfit()));
        }
    }

    private void printTotalAsset(TotalAssetDto asset) {
        System.out.print("\n\n" + "*".repeat(80) + " [2024년 총정산] " + "*".repeat(80) + "\n");
        System.out.println("-".repeat(170));
        System.out.printf("%-20s| %-20s | %-20s | %-20s\n",
                "지출계", "매출계", "순수익", "순수익 증감률");
        System.out.println("-".repeat(170));

        System.out.printf("%-20.0s원 | %-20.0s원 | %-20.0s원 | %-20.2f%%\n",
                new DecimalFormat("###,###").format(asset.getSumExpense()),
                new DecimalFormat("###,###").format(asset.getSumProfit()),
                new DecimalFormat("###,###").format(asset.getNetProfit()),
                asset.getNetProfitPer());
    }
}