package domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Vendor {
    private Integer id;
    private String name;
    private String addr;
    private String tel;
    private String fax;
    private String email;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
    private Boolean status;
}
