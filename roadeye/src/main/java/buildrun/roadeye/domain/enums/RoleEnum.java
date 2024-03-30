package buildrun.roadeye.domain.enums;

import lombok.Getter;

@Getter
public enum RoleEnum {
    ADMIN("admin"),
    USER("user"),
    DRIVER("driver"),
    RESPONSIBLE("responsible"),
    STUDANT("studant");
    private String role;
    RoleEnum(String role) {
        this.role = role;
    }
}
