package buildrun.roadeye.domain.enums;

public enum StatusRoute {
    WAITINGTOSTART("Aguardando iniciar rota"),
    ROUTESTARTED("Rota inicializada"),
    ROUTEFINISHED("Rota finalizada");

    private String routeStatus;
    StatusRoute(String routeStatus) {
        this.routeStatus = routeStatus;
    }

    public static boolean isRouteStatusValid(StatusRoute statusRoute) {
        try {
            StudentStatus.valueOf(statusRoute.name());
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }
}
