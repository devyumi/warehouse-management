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

    public Vendor(Integer id, String name, String addr, String tel, String fax, String email, LocalDateTime regDate, LocalDateTime modDate, Boolean status) {
        this.id = id;
        this.name = name;
        this.addr = addr;
        this.tel = tel;
        this.fax = fax;
        this.email = email;
        this.regDate = regDate;
        this.modDate = modDate;
        this.status = status;
    }
}
