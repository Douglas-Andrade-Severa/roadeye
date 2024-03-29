package buildrun.roadeye.domain.enums;

public enum StatusEnum {
    ACTIVATE("activate"),
    DISABLED("disabled");

    private final String status;

    StatusEnum(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
}
