package org.qualihub.resume.web.thymleaf;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

@Component
public class DateUtils {

    public String dateRange(Locale locale, LocalDate startDate, LocalDate endDate) {
        Locale acualLocal = Locale.ENGLISH;
        if (locale != null) {
            acualLocal = locale;
        }
        if (startDate == null && endDate == null)
            return "";

        String startDateStr = "";
        if (startDate != null) {
            String startMonth = startDate.getMonth().getDisplayName(TextStyle.FULL, acualLocal);
            int startYear = startDate.getYear();
            startDateStr = startMonth + ", " + startYear;
        }
        String endDateStr = "Present";
        if (endDate != null) {
            String startMonth = endDate.getMonth().getDisplayName(TextStyle.FULL, acualLocal);
            int startYear = endDate.getYear();
            endDateStr = startMonth + " " + startYear;
        }
        return startDateStr + " - " + endDateStr;
    }

    public String yearRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null && endDate == null)
            return "";

        String startDateStr = "";
        if (startDate != null) {
            int startYear = startDate.getYear();
            startDateStr = "" + startYear;
        }
        String endDateStr = "Present";
        if (endDate != null) {
            int startYear = endDate.getYear();
            endDateStr = "" + startYear;
        }
        return startDateStr + " - " + endDateStr;
    }
}
