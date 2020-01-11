package mlm.tool.mungwin.com.mlmtool.services.utilities;

import java.time.Clock;
import java.time.LocalDateTime;

/**
 * @author Nnouka Stephen
 *
 */
public class DateTimeProvider {
    protected static Clock clock = Clock.systemDefaultZone();
    public static LocalDateTime now(){
        return LocalDateTime.now(clock);
    }
}
