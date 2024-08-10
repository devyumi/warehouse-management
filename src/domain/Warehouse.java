package domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Warehouse {
    private Integer id;
    private User user;
    private WarehouseType warehouseType;
    private String name;
    private Region region;
    private String detailAddress;
    private String contact;
    private Double maxCapacity;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
