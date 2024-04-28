package buildrun.roadeye.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Data
@Entity
@Table(name = "tb_user_school")
public class StudentSchool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userSchool_id", nullable = false, unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;
}
