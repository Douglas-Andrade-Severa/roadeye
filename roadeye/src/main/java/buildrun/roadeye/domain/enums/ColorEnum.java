package buildrun.roadeye.domain.enums;

public enum ColorEnum {
    WHITE("white"),
    BLACK("black"),
    RED("red"),
    BLUE("blue"),
    YELLOW("yellow"),
    GREEN("green"),
    SILVER("silver"),
    GRAY("gray"),
    ORANGE("orange"),
    PINK("pink"),
    BROWN("brown"),
    PURPLE("purple");
    private final String value;
    ColorEnum(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
