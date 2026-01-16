package com.demo.springbatchdemo;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class DateTimeUtil {

    public static LocalDate parseDate(String dateStr) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return LocalDate.parse(dateStr, format);
    }

    public static LocalTime parseTime(String timeStr) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("H:mm");
        return LocalTime.parse(timeStr, format);
    }

}
