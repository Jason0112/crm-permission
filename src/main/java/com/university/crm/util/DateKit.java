package com.university.crm.util;

import org.apache.commons.lang3.StringUtils;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.HOUR_OF_DAY;
import static java.time.temporal.ChronoField.MILLI_OF_SECOND;
import static java.time.temporal.ChronoField.MINUTE_OF_HOUR;
import static java.time.temporal.ChronoField.SECOND_OF_MINUTE;

/**
 * date: 2017/8/6
 * description : 日期工具,jdk8
 *
 * @author : zhencai.cheng
 */
public final class DateKit {

    public static final String DEFAULT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_SYSTEM_DATE_TIME = DEFAULT_DATE_TIME;
    public static final String DATE_TIME = "yyyyMMddHHmmss";
    public static final String DEFAULT_DATE_HOURS_TIME = "yyyy-MM-dd HH";
    public static final String DATE_HOURS_TIME = "yyyyMMddHH";
    public static final String DEFAULT_DATE = "yyyy-MM-dd";
    public static final String DATE = "yyyyMMdd";

    private DateKit() {
        throw new IllegalAccessError("DateKit class");
    }

    public static Date now() {
        return covertDate(LocalDateTime.now());
    }

    /**
     * 增加年
     *
     * @param date   日期 2017-06-21 16:52:38
     * @param amount 年可为负数 1
     * @return 计算结果时间 2018-06-21 16:52:38
     */
    public static Date addYears(Date date, int amount) {
        return add(date, amount, ChronoUnit.YEARS);
    }


    public static Date addYears(int amount) {
        return add(now(), amount, ChronoUnit.YEARS);
    }

    /**
     * 增加月
     *
     * @param date   日期 2017-06-21 16:52:38
     * @param amount 可为负数 1
     * @return 计算结果时间 2017-07-21 16:52:38
     */
    public static Date addMonths(Date date, int amount) {
        return add(date, amount, ChronoUnit.MONTHS);
    }

    /**
     * 增加周
     *
     * @param date   日期 2017-06-21 16:53:24
     * @param amount 可为负数 1
     * @return 计算结果时间 2017-06-28 16:53:24
     */
    public static Date addWeeks(Date date, int amount) {
        return add(date, amount, ChronoUnit.WEEKS);
    }

    /**
     * 增加日
     *
     * @param date   日期 2017-06-21 16:53:24
     * @param amount 可为负数 1
     * @return 计算结果时间 2017-06-22 16:53:24
     */
    public static Date addDays(Date date, int amount) {
        return add(date, amount, ChronoUnit.DAYS);
    }

    public static Date addDays(int amount) {
        return add(now(), amount, ChronoUnit.DAYS);
    }

    /**
     * 增加时
     *
     * @param date   日期 2017-06-21 16:53:24
     * @param amount 可为负数 1
     * @return 计算结果时间 2017-06-21 17:53:24
     */
    public static Date addHours(Date date, int amount) {
        return add(date, amount, ChronoUnit.HOURS);
    }

    /**
     * 增加分
     *
     * @param date   日期 2017-06-21 16:53:24
     * @param amount 可为负数 1
     * @return 计算结果时间 2017-06-21 16:54:24
     */
    public static Date addMinutes(Date date, int amount) {
        return add(date, amount, ChronoUnit.MINUTES);
    }

    /**
     * 增加秒
     *
     * @param date   日期 2017-06-21 16:53:24
     * @param amount 可为负数 1
     * @return 计算结果时间 2017-06-21 16:53:25
     */
    public static Date addSeconds(Date date, int amount) {
        return add(date, amount, ChronoUnit.SECONDS);
    }

    /**
     * 增加毫秒
     *
     * @param date   日期 2017-06-21 16:53:24.112
     * @param amount 可为负数 1
     * @return 计算结果时间 2017-06-21 16:53:24.113
     */
    public static Date addMilliseconds(Date date, int amount) {
        return add(date, amount, ChronoUnit.MILLENNIA);
    }

    public static Date add(Date date, int amount, ChronoUnit unit) {
        if (date == null) {
            return null;
        } else {
            LocalDateTime time = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            time = time.minus(-amount, unit);
            return covertDate(time);
        }
    }

    public static LocalDateTime add(LocalDateTime time, int amount, ChronoUnit unit) {
        if (time == null) {
            time = LocalDateTime.now();
        }
        time = time.minus(-amount, unit);
        return time;
    }


    public static LocalDate add(LocalDate time, int amount, ChronoUnit unit) {
        if (time == null) {
            time = LocalDate.now();
        }
        time = time.minus(-amount, unit);
        return time;

    }


    public static String format(LocalDateTime date,String format) {
        if(StringUtils.isBlank(format)){
            format = DEFAULT_SYSTEM_DATE_TIME;
        }
        return DateTimeFormatter.ofPattern(format).format(date);
    }

    public static String format(LocalDateTime date) {
        return format(date,null);
    }

    public static String format(LocalDate date,String format) {
        if(StringUtils.isBlank(format)){
            format = DEFAULT_DATE;
        }
        return DateTimeFormatter.ofPattern(format).format(date);
    }

    public static String format(LocalDate date) {
        return format(date,null);
    }


    public static String format(Date date) {
        return format(date, DEFAULT_SYSTEM_DATE_TIME);
    }

    /**
     * 格式化日期
     *
     * @param date   日期 Wed Jun 21 16:55:14 CST 2017
     * @param format 格式  yyyy-MM-dd HH:mm:ss
     * @return 字符串日期 2017-06-21 16:55:14
     */
    public static String format(Date date, String format) {
        if (StringUtils.isBlank(format)) {
            format = DEFAULT_SYSTEM_DATE_TIME;
        }
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        } else {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
            LocalDateTime time = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
            return time.format(dtf);
        }
    }

    public static Date parser(String str) {
        return parser(str, null);
    }

    /**
     * 字符串日期转换成
     *
     * @param str    字符日期 2017-06-21 16:55:14
     * @param format 格式 yyyy-MM-dd HH:mm:ss
     * @return 日期 Wed Jun 21 16:55:14 CST 2017
     */
    public static Date parser(String str, String format) {
        DateTimeFormatter formatter;
        if (StringUtils.isBlank(format)) {
            if (str.length() > DateKit.DEFAULT_DATE.length()) {
                formatter = DateTimeFormatter.ofPattern(DateKit.DEFAULT_DATE_TIME);
            } else {
                formatter = DateTimeFormatter.ofPattern(DateKit.DEFAULT_DATE);
            }
        } else {
            formatter = DateTimeFormatter.ofPattern(format);
        }

        LocalDateTime dateTime;
        if (str.length() > DEFAULT_DATE.length()) {
            dateTime = LocalDateTime.parse(str, formatter);
        } else {
            LocalDate date = LocalDate.parse(str, formatter);
            dateTime = date.atStartOfDay();
        }
        return covertDate(dateTime);
    }

    /**
     * 当前时间: 2017-06-21 16:55:14  ->2017-06-01 00:00:00
     *
     * @return 当月第一天日期
     */
    public static Date getMothFirstDay() {
        LocalDateTime dateTime = with(getTemporalAdjuster(DAY_OF_MONTH, false), null)
                .with(getTemporalAdjuster(HOUR_OF_DAY, false))
                .with(getTemporalAdjuster(MINUTE_OF_HOUR, false))
                .with(getTemporalAdjuster(SECOND_OF_MINUTE, false))
                .with(getTemporalAdjuster(MILLI_OF_SECOND, false));
        return covertDate(dateTime);
    }

    /**
     * @param date
     * @return 当前时间月份第一天日期
     */
    public static Date getMothFirstDay(Date date) {
        LocalDateTime dateTime = with(getTemporalAdjuster(DAY_OF_MONTH, false), date)
                .with(getTemporalAdjuster(HOUR_OF_DAY, false))
                .with(getTemporalAdjuster(MINUTE_OF_HOUR, false))
                .with(getTemporalAdjuster(SECOND_OF_MINUTE, false))
                .with(getTemporalAdjuster(MILLI_OF_SECOND, false));
        return covertDate(dateTime);
    }

    /**
     * 2017-06-21 16:55:14 ->2017-06-30 23:59:59
     *
     * @return 当月最后一天日期
     */
    public static Date getMothLastDay() {
        LocalDateTime dateTime = with(getTemporalAdjuster(DAY_OF_MONTH, true), null)
                .with(getTemporalAdjuster(HOUR_OF_DAY, true))
                .with(getTemporalAdjuster(MINUTE_OF_HOUR, true))
                .with(getTemporalAdjuster(SECOND_OF_MINUTE, true))
                .with(getTemporalAdjuster(MILLI_OF_SECOND, true));

        return covertDate(dateTime);
    }

    /**
     * @param date 当前时间
     * @return 当前时间月最后一天日期
     */
    public static Date getMothLastDay(Date date) {
        LocalDateTime dateTime = with(getTemporalAdjuster(DAY_OF_MONTH, true), date)
                .with(getTemporalAdjuster(HOUR_OF_DAY, true))
                .with(getTemporalAdjuster(MINUTE_OF_HOUR, true))
                .with(getTemporalAdjuster(SECOND_OF_MINUTE, true))
                .with(getTemporalAdjuster(MILLI_OF_SECOND, true));

        return covertDate(dateTime);
    }

    /**
     * @return 当前时间的周第一天
     */
    public static Date getWeekFirstDay() {
        LocalDateTime dateTime = with(getTemporalAdjuster(HOUR_OF_DAY, false), null)
                .with(getTemporalAdjuster(DAY_OF_WEEK, false))
                .with(getTemporalAdjuster(MINUTE_OF_HOUR, false))
                .with(getTemporalAdjuster(SECOND_OF_MINUTE, false))
                .with(getTemporalAdjuster(MILLI_OF_SECOND, false));
        return covertDate(dateTime);
    }

    public static Date getWeekFirstDay(Date date) {
        LocalDateTime dateTime = with(getTemporalAdjuster(HOUR_OF_DAY, false), date)
                .with(getTemporalAdjuster(DAY_OF_WEEK, false))
                .with(getTemporalAdjuster(MINUTE_OF_HOUR, false))
                .with(getTemporalAdjuster(SECOND_OF_MINUTE, false))
                .with(getTemporalAdjuster(MILLI_OF_SECOND, false));
        return covertDate(dateTime);
    }

    /**
     * @return 当前时间的周最一天
     */
    public static Date getWeekLastDay() {
        LocalDateTime dateTime = with(getTemporalAdjuster(HOUR_OF_DAY, true), null)
                .with(getTemporalAdjuster(DAY_OF_WEEK, true))
                .with(getTemporalAdjuster(MINUTE_OF_HOUR, true))
                .with(getTemporalAdjuster(SECOND_OF_MINUTE, true))
                .with(getTemporalAdjuster(MILLI_OF_SECOND, true));
        return covertDate(dateTime);
    }

    public static Date getWeekLastDay(Date date) {
        LocalDateTime dateTime = with(getTemporalAdjuster(HOUR_OF_DAY, true), date)
                .with(getTemporalAdjuster(DAY_OF_WEEK, true))
                .with(getTemporalAdjuster(MINUTE_OF_HOUR, true))
                .with(getTemporalAdjuster(SECOND_OF_MINUTE, true))
                .with(getTemporalAdjuster(MILLI_OF_SECOND, true));
        return covertDate(dateTime);
    }


    /**
     * 2017-06-21 16:55:14 -> 2017-06-21 00:00:00
     *
     * @return 当天零点
     */
    public static Date getDayFirstHour() {
        LocalDateTime dateTime = with(getTemporalAdjuster(HOUR_OF_DAY, false), null)
                .with(getTemporalAdjuster(MINUTE_OF_HOUR, false))
                .with(getTemporalAdjuster(SECOND_OF_MINUTE, false))
                .with(getTemporalAdjuster(MILLI_OF_SECOND, false));
        return covertDate(dateTime);
    }

    /**
     * @param date 当前时间
     * @return 当前时间零点
     */
    public static Date getDayFirstHour(Date date) {
        LocalDateTime dateTime = with(getTemporalAdjuster(HOUR_OF_DAY, false), date)
                .with(getTemporalAdjuster(MINUTE_OF_HOUR, false))
                .with(getTemporalAdjuster(SECOND_OF_MINUTE, false))
                .with(getTemporalAdjuster(MILLI_OF_SECOND, false));
        return covertDate(dateTime);
    }

    /**
     * 2017-06-21 16:55:14 -> 2017-06-21 23:59:59
     *
     * @return 当天最后一小时
     */
    public static Date getDayLastHour() {
        LocalDateTime dateTime = with(getTemporalAdjuster(HOUR_OF_DAY, true), null)
                .with(getTemporalAdjuster(MINUTE_OF_HOUR, true))
                .with(getTemporalAdjuster(SECOND_OF_MINUTE, true))
                .with(getTemporalAdjuster(MILLI_OF_SECOND, true));
        return covertDate(dateTime);
    }

    /**
     * @param date 当前时间
     * @return 当前时间下一天的零点前一秒
     */
    public static Date getDayLastHour(Date date) {
        LocalDateTime dateTime = with(getTemporalAdjuster(HOUR_OF_DAY, true), date)
                .with(getTemporalAdjuster(MINUTE_OF_HOUR, false))
                .with(getTemporalAdjuster(SECOND_OF_MINUTE, false))
                .with(getTemporalAdjuster(MILLI_OF_SECOND, false));
        return covertDate(dateTime);
    }


    private static TemporalAdjuster getTemporalAdjuster(ChronoField field, boolean max) {
        if (max) {
            return (temporal) -> temporal.with(field, temporal.range(field).getMaximum());
        } else {
            return (temporal) -> temporal.with(field, temporal.range(field).getMinimum());
        }
    }

    private static LocalDateTime with(TemporalAdjuster temporalAdjuster, Date date) {
        LocalDateTime time;
        if (date == null) {
            time = LocalDateTime.now();
        } else {
            time = covertLocalDateTime(date);
        }
        return time.with(temporalAdjuster);
    }

    /**
     * java.time.LocalDateTime转换成java.util.Date
     *
     * @param dateTime java.time.LocalDateTime
     * @return java.util.Date
     */
    public static Date covertDate(LocalDateTime dateTime) {
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * java.time.LocalDate转换成java.util.Date
     *
     * @param dateTime java.time.LocalDateTime
     * @return java.util.Date
     */
    public static Date covertDate(LocalDate dateTime) {
        return Date.from(dateTime.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * java.util.Date转换成java.time.LocalDateTime
     *
     * @param date java.util.Date
     * @return java.time.LocalDateTime
     */
    public static LocalDateTime covertLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * java.util.Date转换成java.time.LocalDateTime
     *
     * @param date java.util.Date
     * @return java.time.LocalDateTime
     */
    public static LocalDate covertLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * 一天还剩余多少时间（s）
     */
    public static long remainTime() {
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalDate today = LocalDate.now();
        LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);
        LocalDateTime tomorrowMidnight = todayMidnight.plusDays(1);
        return TimeUnit.NANOSECONDS.toSeconds(Duration.between(LocalDateTime.now(), tomorrowMidnight).toNanos());
    }

    public static Date[] build(Date[] times, int size) {
        Date[] dates;
        int i = 0;
        if (times == null) {
            dates = new Date[size];
        } else {
            dates = Arrays.copyOf(times, size);
            i = times.length;
        }
        while (size > i) {
            dates[size - 1] = new Date();
            size--;
        }
        return dates;
    }

}