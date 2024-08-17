package domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Vendor {
    private Integer id;
    private String name;
}
