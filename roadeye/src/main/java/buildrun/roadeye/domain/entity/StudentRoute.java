package buildrun.roadeye.domain.entity;

import buildrun.roadeye.domain.enums.ConfimationStudentEnum;
import buildrun.roadeye.domain.enums.StatusRoute;
import buildrun.roadeye.domain.enums.StudentStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;


@Data
@Entity
@Table(name = "tb_route")
public class StudentRoute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "route_id", nullable = false, unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;

    @Column(nullable = false,name = "local_date")
    private LocalDate localDate;

    @Column(nullable = false, name = "status_student")
    @Enumerated(EnumType.STRING)
    private StudentStatus studentStatus;

    @Column(nullable = false, name = "status_route")
    @Enumerated(EnumType.STRING)
    private StatusRoute statusRoute;

    @Column(nullable = false, name = "status_confirmation_student")
    @Enumerated(EnumType.STRING)
    private ConfimationStudentEnum confimationStudentEnum;

    @Column(name = "image_data", columnDefinition = "LONGBLOB")
    private byte[] imageData;
}
