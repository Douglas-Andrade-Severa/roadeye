package buildrun.roadeye.domain.enums;

import lombok.Getter;

@Getter
public enum RouteStatus {
    IWONTGO("Não irei"),           //Não irei
    ONEWAYONLY("Somente ida"),     //Somente ida
    ONLYAROUND("Somente a volta"), //Somente volta
    ROUNDTRIP("Ida e volta");      //Ida e volta

    private String routeStatus;
    RouteStatus(String routeStatus) {
        this.routeStatus = routeStatus;
    }
}
