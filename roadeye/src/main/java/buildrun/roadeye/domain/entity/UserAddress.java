package buildrun.roadeye.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "tb_user_address")
public class UserAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

}
