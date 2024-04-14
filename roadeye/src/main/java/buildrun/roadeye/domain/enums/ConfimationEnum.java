package buildrun.roadeye.domain.enums;

import lombok.Getter;

@Getter
public enum ConfimationEnum {
    CONFIRM("Confirmado, entrou no onibus."), //Não irei
    CANCEL("Usuario, cancelou o transporte."),//Somente ida
    ABSENT("Usuario aguardando onibus."),     //Somente volta
    NOTENTER("Usuario não entrou no onibus.");//Ida e volta

    private String confimationStatus;

    ConfimationEnum(String confimationStatus) {
        this.confimationStatus = confimationStatus;
    }
}
