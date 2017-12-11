package com.lyldj.springboot.common.utils;

import org.joda.time.DateTime;

import java.util.Date;
import java.util.Objects;

/**
 * Format date and parse string to date
 */
public class DateUtils {

    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String TIME_PATTERN = "HH:mm:ss";
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_MILLI_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";

    public static final String DATE_NO_PATTERN = "yyyyMMdd";
    public static final String DATE_TIME_NO_PATTERN = "yyyyMMddHHmmss";
    public static final String DATE_TIME_MILLI_NO_PATTERN = "yyyyMMddHHmmssSSS";
    public static final String SHORT_DATE_TIME_MILLI_NO_PATTERN = "yyMMddHHmmssSSS";

    private static final Long SECOND_MILLI = 1000L;
    private static final Long MINUTE_MILLI = SECOND_MILLI * 60;
    private static final Long HOUR_MILLI = MINUTE_MILLI * 60;
    private static final Long DAY_MILLI = HOUR_MILLI * 24;

    public static String format(Date date, String format) {
        if(Objects.isNull(date)){
            return null;
        }
        return new DateTime(date).toString(format);
    }

    public static String formatDate(Date date) {
        return format(date, DATE_PATTERN);
    }

    public static String formatTime(Date date) {
        return format(date, TIME_PATTERN);
    }

    public static String formatDateTime(Date date) {
        return format(date, DATE_TIME_PATTERN);
    }

    public static String formatDateTimeMilli(Date date) {
        return format(date, DATE_TIME_MILLI_PATTERN);
    }

    public static String formatDateNo(Date date) {
        return format(date, DATE_NO_PATTERN);
    }

    public static String formatDateTimeNo(Date date) {
        return format(date, DATE_TIME_NO_PATTERN);
    }

    public static String formatDateTimeMilliNo(Date date) {
        return format(date, DATE_TIME_MILLI_NO_PATTERN);
    }

    public static Date plusYears(Date date, Integer increment) {
        return new DateTime(date).plusYears(increment).toDate();
    }

    public static Date plusMonths(Date date, Integer increment) {
        return new DateTime(date).plusMonths(increment).toDate();
    }

    public static Date plusDays(Date date, Integer increment) {
        return new DateTime(date).plusDays(increment).toDate();
    }

    public static Date plusHours(Date date, Integer increment) {
        return new DateTime(date).plusHours(increment).toDate();
    }

    public static Date plusMinutes(Date date, Integer increment) {
        return new DateTime(date).plusMinutes(increment).toDate();
    }

    public static Date plusSeconds(Date date, Integer increment) {
        return new DateTime(date).plusSeconds(increment).toDate();
    }

    public static Date plusMillis(Date date, Integer increment) {
        return new DateTime(date).plusMillis(increment).toDate();
    }

    public static Date minusYears(Date date, Integer increment) {
        return new DateTime(date).minusYears(increment).toDate();
    }

    public static Date minusMonths(Date date, Integer increment) {
        return new DateTime(date).minusMonths(increment).toDate();
    }

    public static Date minusDays(Date date, Integer increment) {
        return new DateTime(date).minusDays(increment).toDate();
    }

    public static Date minusHours(Date date, Integer increment) {
        return new DateTime(date).minusHours(increment).toDate();
    }

    public static Date minusMinutes(Date date, Integer increment) {
        return new DateTime(date).minusMinutes(increment).toDate();
    }

    public static Date minusSeconds(Date date, Integer increment) {
        return new DateTime(date).minusSeconds(increment).toDate();
    }

    public static Date minusMillis(Date date, Integer increment) {
        return new DateTime(date).minusMillis(increment).toDate();
    }

    public static Date getDate(String dateStr) {
        return new DateTime(dateStr).toDate();
    }

    public static Date getDate(Long millis) {
        return new DateTime(millis).toDate();
    }

    public static Date getDateBegin(Date date) {
        return new DateTime(date).toLocalDate().toDate();
    }

    public static Date getDateEnd(Date date) {
        return new Date(new DateTime(date).plusDays(1).toDate().getTime() - 1);
    }

}
