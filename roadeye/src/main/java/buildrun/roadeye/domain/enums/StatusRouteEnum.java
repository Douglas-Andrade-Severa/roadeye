package buildrun.roadeye.domain.enums;

public enum StatusRouteEnum {
    WAITINGTOSTART("Aguardando iniciar rota"),
    ROUTESTARTED("Rota inicializada"),
    ROUTEFINISHED("Rota finalizada");

    private String routeStatus;
    StatusRouteEnum(String routeStatus) {
        this.routeStatus = routeStatus;
    }

    public static boolean isRouteStatusValid(StatusRouteEnum statusRouteEnum) {
        try {
            StudentStatusEnum.valueOf(statusRouteEnum.name());
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }
}
