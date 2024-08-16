package domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Product {
    private Integer id;
    private ProductCategory productCategory;
    private String code;
    private String name;
    private Double costPrice;
    private String manufacturer;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
    private Vendor vendor;

    public Product(Integer id, ProductCategory productCategory, String code, String name, Double costPrice, String manufacturer, LocalDateTime regDate, LocalDateTime modDate, Vendor vendor) {
        this.id = id;
        this.productCategory = productCategory;
        this.code = code;
        this.name = name;
        this.costPrice = costPrice;
        this.manufacturer = manufacturer;
        this.regDate = regDate;
        this.modDate = modDate;
        this.vendor = vendor;
    }
}
