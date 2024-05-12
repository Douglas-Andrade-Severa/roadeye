package buildrun.roadeye.domain.enums;

import lombok.Getter;

@Getter
public enum ConfimationStudentEnum {
    CONFIRM( "Confirmado, entrou no onibus."),
    CANCEL(  "Usuario, cancelou o transporte."),
    ABSENT(  "Usuario aguardando onibus."),
    NOTENTER("Usuario não entrou no onibus.");

    private String confimationStudent;

    ConfimationStudentEnum(String confimationStudent) {
        this.confimationStudent = confimationStudent;
    }
}
