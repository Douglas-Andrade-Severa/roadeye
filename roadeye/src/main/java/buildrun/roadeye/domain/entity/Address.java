package buildrun.roadeye.domain.entity;

import buildrun.roadeye.domain.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;
    @Column(nullable = false)
    private String postCode;
    @Column(nullable = false)
    private String street;
    private String neighborhood;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String state;
    @Column(nullable = false)
    private String country;
    private String complement;
    @Column(nullable = false)
    private Long number;
    @Column(nullable = false)
    private Double latitude;
    @Column(nullable = false)
    private Double longitude;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEnum statusEnum;
}
