package domain;

import lombok.Getter;

@Getter
public class StockSection {
    private Integer id;
    private Warehouse warehouse;
    private Stock stock;
    private String name;
    private Double width;
    private Double height;
}
