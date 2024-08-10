package domain;

import lombok.Getter;

@Getter
public class User {
    private Integer id;
    private String name;
    private String phoneNumber;
    private String loginEmail;
    private String password;
    private RoleType roleType;
}
