package domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Region {
    private Integer id;
    private String code;
    private String name;
}
