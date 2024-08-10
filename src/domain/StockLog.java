package domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StockLog {
    private Integer id;
    private LocalDateTime modDate;
    private String code;
    private String name;
    private String modType;
    private Integer previousQuantity;
    private Integer afterQuantity;
}
