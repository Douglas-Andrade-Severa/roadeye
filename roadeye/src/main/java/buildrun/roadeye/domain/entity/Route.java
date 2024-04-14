package buildrun.roadeye.domain.entity;

import buildrun.roadeye.domain.enums.ConfimationEnum;
import buildrun.roadeye.domain.enums.RouteStatus;
import buildrun.roadeye.domain.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "tb_route")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "route_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "userAddress_id")
    private UserAddress userAddress;
    @ManyToOne
    @JoinColumn(name = "schoolAddress_id")
    private SchoolAddress schoolAddress;
    @Column(nullable = false)
    private LocalDate localDate;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RouteStatus routeStatus;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEnum statusEnum;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ConfimationEnum confimationEnum;
    @ManyToOne
    @JoinColumn(name = "routeNumber_id")
    private RouteNumber routeNumber;
}
