package buildrun.roadeye.domain.enums;

public enum PeriodEnum {
    MORNING("Matutino"),  //Manhã
    EVENING("Vespertino"),//Tarde
    NIGHT("Noturno");     //Noite
    private String periodEnum;
    PeriodEnum(String PeriodEnum) {
        this.periodEnum = periodEnum;
    }

    public static boolean isPeriodValid(PeriodEnum periodEnum) {
        try {
            PeriodEnum.valueOf(periodEnum.name());
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }
}
