package buildrun.roadeye.domain.entity;

import buildrun.roadeye.domain.enums.ColorEnum;
import buildrun.roadeye.domain.enums.StatusEnum;
import buildrun.roadeye.domain.enums.TypeVehicleEnum;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_vehicle")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicle_id", nullable = false, unique = true)
    private Long id;
    @Column(nullable = false)
    private String brand;
    @Column(nullable = false)
    private String model;
    @Column(nullable = false)
    private String licensePlate;
    @Column(nullable = false)
    private String numberRenavan;
    @Column(nullable = false)
    private int yearManufacturing;
    @Column(nullable = false)
    private int capacityPassengers;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeVehicleEnum typeVehicleEnum;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ColorEnum colorEnum;
    @Enumerated(EnumType.STRING)
    private StatusEnum statusEnum;

    public void setTypeVehicleEnum(TypeVehicleEnum typeVehicleEnum) {
        this.typeVehicleEnum = typeVehicleEnum;
        this.capacityPassengers = typeVehicleEnum.getMaximumCapacity();
    }

}
