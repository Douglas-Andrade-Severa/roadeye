package buildrun.roadeye.domain.enums;

import lombok.Getter;

@Getter
public enum StudentStatusEnum {
    IWONTGO("Não irei"),           //Não irei
    ONEWAYONLY("Somente ida"),     //Somente ida
    ONLYAROUND("Somente a volta"), //Somente volta
    ROUNDTRIP("Ida e volta");      //Ida e voltar

    private String routeStatus;
    StudentStatusEnum(String routeStatus) {
        this.routeStatus = routeStatus;
    }

    public static boolean isStudentStatusValid(StudentStatusEnum studentStatusEnum) {
        try {
            StudentStatusEnum.valueOf(studentStatusEnum.name());
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }
}
