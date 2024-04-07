package buildrun.roadeye.rest.dto;

import buildrun.roadeye.domain.entity.Vehicle;
import buildrun.roadeye.domain.enums.ColorEnum;
import buildrun.roadeye.domain.enums.StatusEnum;
import buildrun.roadeye.domain.enums.TypeVehicleEnum;
public record VehicleDto(
        String brand,
        String model,
        String licensePlate,
        String numberRenavam,
        int yearManufacturing,
        int capacityPassengers,
        TypeVehicleEnum typeVehicleEnum,
        ColorEnum colorEnum,
        StatusEnum statusEnum
) {
    public static VehicleDto fromEntity(Vehicle vehicle) {
        return new VehicleDto(
                vehicle.getBrand(),
                vehicle.getModel(),
                vehicle.getLicensePlate(),
                vehicle.getNumberRenavam(),
                vehicle.getYearManufacturing(),
                vehicle.getCapacityPassengers(),
                vehicle.getTypeVehicleEnum(),
                vehicle.getColorEnum(),
                vehicle.getStatusEnum()
        );
    }
}
