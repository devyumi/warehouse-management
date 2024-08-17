package domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private Integer id;
    private String name;
    private String phoneNumber;
    private String loginEmail;
    private String password;
    private RoleType roleType;
}
