package buildrun.roadeye.domain.enums;

import lombok.Getter;

@Getter
public enum RoleEnum {
    ADMIN("admin"),
    USER("user"),
    DRIVER("driver"),
    RESPONSIBLE("responsible"),
    STUDENT("student");
    private String role;
    RoleEnum(String role) {
        this.role = role;
    }

    public static boolean contains(String role) {
        for (RoleEnum r : RoleEnum.values()) {
            if (r.getRole().equals(role)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isRoleValid(RoleEnum roleEnum) {
        try {
            RoleEnum.valueOf(roleEnum.name());
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }
}
