package domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Stock {
    private Integer id;
    private Product product;
    private User user;
    private Double width;
    private Double height;
    private Integer quantity;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
