package domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Stock {
    private Integer id;
    private Product product;
    private User user;
    private Double width;
    private Double height;
    private Integer quantity;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
    private LocalDateTime manufacturedDate;
    private LocalDateTime expirationDate;

    public Stock(Integer id, Product product, User user, Double width, Double height, Integer quantity, LocalDateTime regDate, LocalDateTime modDate, LocalDateTime manufacturedDate, LocalDateTime expirationDate) {
        this.id = id;
        this.product = product;
        this.user = user;
        this.width = width;
        this.height = height;
        this.quantity = quantity;
        this.regDate = regDate;
        this.modDate = modDate;
        this.manufacturedDate = manufacturedDate;
        this.expirationDate = expirationDate;
    }
}
