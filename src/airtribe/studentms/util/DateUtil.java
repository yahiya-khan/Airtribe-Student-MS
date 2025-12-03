package airtribe.studentms.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class DateUtil {
    public static String todayFormatted() {
        return LocalDate.now().format(DateTimeFormatter.ISO_DATE);
    }
}
