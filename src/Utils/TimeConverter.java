package Utils;

import java.time.LocalDateTime;
import java.time.ZoneId;


public class TimeConverter {

    /**
     * @param ldt Takes a LocalDateTime object as a parameter.
     * @return returns a LocalDateTime object after converting it to EST time.
     *
     * Converts LocalDateTime passed to EST time.
     */
    public static LocalDateTime getLocalDateTimeInEST (LocalDateTime ldt) {

        ZoneId localZone = ZoneId.systemDefault();
        ZoneId estZone = ZoneId.of("America/New_York");
        LocalDateTime ldtEST = ldt.atZone(localZone).withZoneSameInstant(estZone).toLocalDateTime();

        return ldtEST;
    }




}
