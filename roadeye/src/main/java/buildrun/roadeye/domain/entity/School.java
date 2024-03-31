package buildrun.roadeye.domain.entity;

import buildrun.roadeye.domain.enums.StatusEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_school")
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "school_id")
    private Long schoolId;
    private String name;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEnum statusEnum;

    public Long getSchoolId() {
        return schoolId;
    }
    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public StatusEnum getStatusEnum() {
        return statusEnum;
    }
    public void setStatusEnum(StatusEnum statusEnum) {
        this.statusEnum = statusEnum;
    }
}
