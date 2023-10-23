package com.busticketbooking.busticketbooking.Utils;

import com.busticketbooking.busticketbooking.Utils.AttrWrapper;
import com.busticketbooking.busticketbooking.models.Booking;

import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class ConvertUtil {
    public static int convert(String str) throws NumberFormatException {
        return Integer.parseInt(str);
    }

    public static String formatTime(Time sqlTime) {
        // Convert java.sql.Time to LocalTime
        LocalTime localTime = sqlTime.toLocalTime();

        // Format LocalTime to display only hours and minutes
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return localTime.format(formatter);
    }

    public static Date convertStringToDate(String dateStr) throws ParseException {
        java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        return new java.sql.Date(date.getTime());
    }
    public static String formatDate(Date sqlDate) {
        // Convert java.sql.Date to LocalDate
        LocalDate localDate = sqlDate.toLocalDate();

        // Format LocalDate to display in 'DD-MM-YYYY'
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return localDate.format(formatter);
    }


    public static List<String> castStringListToList(String string){
        return Arrays.asList(string.split(",", -1));
    }


    public static String floatToHourAndMinutes(float value) {
        int hours = (int) value;
        int minutes = Math.round((value - hours) * 60);
        return hours + " giờ " + minutes + " phút";
    }
    public static List<Integer> getAllSeatNumbers(List<Booking> bookings) {
        if(bookings.size() == 0){
            return Collections.emptyList();
        }
        return bookings.stream()
                .flatMap(booking -> Arrays.stream(booking.getSeatNumber().split(",")))
                .map(Integer::parseInt)
                .distinct()
                .collect(Collectors.toList());
    }

    public static String formatToMoney(float value) {
        // Define the format pattern
        DecimalFormat formatter = new DecimalFormat("#,###.###");

        // Use a dot as a grouping separator instead of a comma
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        formatter.setDecimalFormatSymbols(symbols);

        // Format the value and append 'đ' symbol
        return formatter.format(value) + "đ";
    }
    public static Time calculateEndTime(Time startTime, float duration) {
        // Convert Time to milliseconds
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);
        long startTimeMillis = (calendar.get(Calendar.HOUR_OF_DAY) * 60L + calendar.get(Calendar.MINUTE)) * 60 * 1000;

        // Convert duration to milliseconds
        int hours = (int) duration;
        int minutes = Math.round((duration - hours) * 60);
        long durationMillis = ((hours * 60L) + minutes) * 60 * 1000;

        // Calculate end time in milliseconds
        long endTimeMillis = startTimeMillis + durationMillis;

        // Convert back to Time
        return new Time(endTimeMillis - java.util.TimeZone.getDefault().getRawOffset()); // Adjust for timezone offset
    }
    public static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public static <T extends Serializable> T getAttr(String name, HttpSession sess) {
        @SuppressWarnings("unchecked")
        AttrWrapper<T> attr = (AttrWrapper<T>) sess.getAttribute(name);
        if (attr == null)
            return null;
        if (attr.isValid())
            return attr.value; // Attribute is valid, you can use it

        // Attribute is invalid, timed out, remove it
        sess.removeAttribute(name);
        return null;
    }
}
