package domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Product {
    private Integer id;
    private String code;
    private String name;
    private Double costPrice;
    private String manufacturer;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
