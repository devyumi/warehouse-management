package domain;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class WarehouseContract {
    private Integer id;
    private Warehouse warehouse;
    private User user;
    private Double capacity;
    private Double usableCapacity;
    private LocalDate contractDate;
    private Integer contractPeriod;
}
