package com.jamkrindo.generate.generatesertfkatspr.utils;


import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.TimeZone;

@Service
public class DateTimeUtils {

    public String convertTimeZone(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        return sdf.format(date == null ? new Date() : date);
    }

    public long countBetweenMonth(String from, String to) throws Exception {
        try {
            long monthsBetween = ChronoUnit.MONTHS.between(
                    YearMonth.from(LocalDate.parse(from)),
                    YearMonth.from(LocalDate.parse(to))
            );

            return monthsBetween;
        } catch (Exception e) {
            throw new Exception("Failed to counting month : "+e.getMessage());
        }
    }

    String strToDate(String value) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(value);
    }
}
