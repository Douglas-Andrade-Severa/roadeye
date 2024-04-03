package buildrun.roadeye.domain.entity;

import buildrun.roadeye.domain.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "tb_school")
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "school_id")
    private Long id;
    private String name;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEnum statusEnum;

}
