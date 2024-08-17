package domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WarehouseType {
    private Integer id;
    private String name;
}
