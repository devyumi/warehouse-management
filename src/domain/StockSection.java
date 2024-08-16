package domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StockSection {
    private Integer id;
    private Warehouse warehouse;
    private Stock stock;
    private String name;
    private Double width;
    private Double height;
}
