package buildrun.roadeye.domain.enums;

import lombok.Getter;

@Getter
public enum StudentStatus {
    IWONTGO("Não irei"),           //Não irei
    ONEWAYONLY("Somente ida"),     //Somente ida
    ONLYAROUND("Somente a volta"), //Somente volta
    ROUNDTRIP("Ida e volta");      //Ida e volta

    private String routeStatus;
    StudentStatus(String routeStatus) {
        this.routeStatus = routeStatus;
    }

    public static boolean isStudentStatusValid(StudentStatus studentStatus) {
        try {
            StudentStatus.valueOf(studentStatus.name());
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }
}
