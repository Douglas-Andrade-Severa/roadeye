package buildrun.roadeye.rest.dto;

import buildrun.roadeye.domain.entity.School;
import buildrun.roadeye.domain.enums.StatusEnum;
public record SchoolDto(String name, StatusEnum statusEnum) {
    public static SchoolDto fromEntity(School school) {
        return new SchoolDto(
                school.getName(),
                school.getStatusEnum()
        );
    }
}
