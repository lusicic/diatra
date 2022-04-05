package com.unipu.mobapp.diatra.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CalendarUtils {

    public static LocalDate selectedDate = LocalDate.now();

    public static String monthYear(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    public static String formattedDate(LocalDate date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formatter);
    }

    public static String convertDate(int input) {
        if (input >= 10) {
            return String.valueOf(input);
        } else {
            return "0" + String.valueOf(input);
        }
    }

    public static ArrayList<LocalDate> showDaysInWeek(LocalDate selectedDate)
    {
        ArrayList<LocalDate> days = new ArrayList<>();
        LocalDate current = mondayFirst(selectedDate);
        LocalDate endDate = current.plusWeeks(1);

        while (current.isBefore(endDate))
        {
            days.add(current);
            current = current.plusDays(1);
        }
        return days;
    }

    private static LocalDate mondayFirst(LocalDate current)
    {
        LocalDate oneWeekAgo = current.minusWeeks(1);

        while (current.isAfter(oneWeekAgo))
        {
            if(current.getDayOfWeek() == DayOfWeek.MONDAY)
                return current;

            current = current.minusDays(1);
        }

        return null;
    }

}
