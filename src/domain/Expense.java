package domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class Expense {
    private Integer id;
    private Warehouse warehouse;
    private LocalDate expenseDate;
    private ExpenseCategory category;
    private Double expenseAmount;
    private String description;
    private String payment_method;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    public Expense(Integer id, Warehouse warehouse, LocalDate expenseDate, ExpenseCategory category, Double expenseAmount, String description, String payment_method, LocalDateTime regDate, LocalDateTime modDate) {
        this.id = id;
        this.warehouse = warehouse;
        this.expenseDate = expenseDate;
        this.category = category;
        this.expenseAmount = expenseAmount;
        this.description = description;
        this.payment_method = payment_method;
        this.regDate = regDate;
        this.modDate = modDate;
    }
}
