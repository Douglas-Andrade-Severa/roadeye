package buildrun.roadeye.domain.enums;

public enum TypeVehicleEnum {
    URBAN_BUS(50),
    BUS_RODOVIARIO(40),
    MICRO_BUS(20),
    VAN(15),
    PASSING_CAR(5),
    TRUCK(2),
    MOTORCYCLE(2),
    BICYCLE(1),
    TRACTOR(1),
    OTHER(0);

    private final int maximumCapacity;
    TypeVehicleEnum(int maximumCapacity) {
        this.maximumCapacity = maximumCapacity;
    }
    public int getMaximumCapacity() {
        return maximumCapacity;
    }
}
