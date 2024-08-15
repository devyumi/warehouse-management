package domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExpenseCategory {
    private Integer id;
    private String name;

    public ExpenseCategory(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
