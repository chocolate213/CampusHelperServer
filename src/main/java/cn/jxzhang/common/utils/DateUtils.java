package cn.jxzhang.common.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created on 2016-11-06 16:25
 * <p>Title:       DateUtils</p>
 * <p>Description: Date Utils</p>
 * <p>Company:     [Company Name]</p>
 * <p>Copyright:   Copyright (c) 2016</p>
 *
 * @author <a href=zhangjx_dev@163.com>J.X.Zhang</a>
 * @version 1.0
 */
public class DateUtils {

    private DateUtils() { /* cannot be instantiated */ }

    /**
     * Date format : "yyyy-MM-dd HH:mm:ss"
     */
    private static final SimpleDateFormat YYYY_MM_DD_HH_MM_SS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * Date format : "yyyy-MM-dd HH:mm"
     */
    private static final SimpleDateFormat YYYY_MM_DD_HH_MM = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    /**
     * Date format : "yyyy-MM-dd"
     */
    private static final SimpleDateFormat YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Date format : "HH:mm:ss"
     */
    private static final SimpleDateFormat HH_MM_SS = new SimpleDateFormat("HH:mm:ss");

    /**
     * Date format : "HH:mm"
     */
    private static final SimpleDateFormat HH_MM = new SimpleDateFormat("HH:mm");

    /**
     * 字符串转Date对象
     *
     * @param stringDate 字符串表示的日期对象
     * @return 转换后的Date对象
     */
    public static Date stringToDate(String stringDate) {
        Date date;
        try {
            date = new SimpleDateFormat(DateLength.patternOf(stringDate.length())).parse(stringDate);
        } catch (Exception e) {
            throw new IllegalArgumentException("Unparseable date:" + stringDate + " , the given argument does not have the format yyyy-mm-dd [hh:mm[:ss]]");
        }
        return date;
    }

    /**
     * 字符串转时间戳
     *
     * @param stringDate 字符串表示的日期对象
     * @return 转换后的Timestamp对象
     */
    public static Timestamp stringToTimestamp(String stringDate) {
        Timestamp timestamp;
        try {
            timestamp = Timestamp.valueOf(stringDate);
        } catch (Exception e) {
            throw new IllegalArgumentException("Unparseable date:" + stringDate + " , the given argument does not have the format yyyy-[m]m-[d]d hh:mm:ss[.f...]");
        }
        return timestamp;
    }

    /**
     * 字符串转Calender
     *
     * @param stringDate 字符串表示的日期对象
     * @return 转换后的Calendar对象
     */
    public static Calendar stringToCalender(String stringDate) {
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = new SimpleDateFormat(DateLength.patternOf(stringDate.length())).parse(stringDate);
            calendar.setTime(date);
        } catch (Exception e) {
            throw new IllegalArgumentException("Unparseable date:" + stringDate + " , the given argument does not have the format yyyy-mm-dd [hh:mm[:ss]]");
        }
        return calendar;
    }

    /**
     * 获取指定格式的SimpleDateFormat
     *
     * @param pattern 日期格式
     * @return 指定格式的SimpleDateFormat对象
     */
    private static SimpleDateFormat getSpecifiedDateFormat(String pattern) {
        return new SimpleDateFormat(pattern);
    }

    /**
     * 获取系统当前Calendar
     *
     * @return 以当地时区表示的系统当前日历
     */
    private static Calendar getCalendar() {
        return Calendar.getInstance();
    }

    /**
     * 指定毫秒数表示的日历
     *
     * @param millis 毫秒数
     * @return 指定毫秒数表示的日历
     */
    public static Calendar getCalendar(long millis) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date(millis));
        return instance;
    }

    /**
     * 指定毫秒数表示的日期
     *
     * @param millis 毫秒数
     * @return 指定毫秒数表示的日期
     */
    public static Date getDate(long millis) {
        return new Date(millis);
    }

    /**
     * 指定毫秒数的时间戳
     *
     * @param millis 毫秒数
     * @return 指定毫秒数的时间戳
     */
    public static Timestamp getTimestamp(long millis) {
        return new Timestamp(millis);
    }

    /**
     * 指定字符串表示的时间戳
     *
     * @param millis 毫秒数的字符串表示
     * @return 以字符形式表示的时间戳
     */
    public static Timestamp getTimestamp(String millis) {
        return new Timestamp(Long.parseLong(millis));
    }

    /**
     * 系统当前日期的时间戳
     *
     * @return 系统当前的时间戳
     */
    public static Timestamp getTimestamp() {
        return new Timestamp(new Date().getTime());
    }

    /**
     * 指定日期的时间戳
     *
     * @param date 指定日期
     * @return 指定日期的时间戳
     */
    public static Timestamp getTimestamp(Date date) {
        return new Timestamp(date.getTime());
    }

    /**
     * 指定日历的时间戳
     *
     * @param cal 指定日历
     * @return 指定日历的时间戳
     */
    public static Timestamp getCalendarTimestamp(Calendar cal) {
        return new Timestamp(cal.getTime().getTime());
    }

    /**
     * 系统时间的毫秒数
     *
     * @return 系统时间的毫秒数
     */
    public static long getMillis() {
        return new Date().getTime();
    }

    /**
     * 指定Calendar的毫秒数
     *
     * @param cal 指定日历
     * @return 指定日历的毫秒数
     */
    public static long getMillis(Calendar cal) {
        return cal.getTime().getTime();
    }

    /**
     * 指定Date的毫秒数
     *
     * @param date 指定日期
     * @return 指定日期的毫秒数
     */
    public static long getMillis(Date date) {
        return date.getTime();
    }

    /**
     * 指定Timestamp的毫秒数
     *
     * @param ts 指定时间戳
     * @return 指定时间戳的毫秒数
     */
    public static long getMillis(Timestamp ts) {
        return ts.getTime();
    }

    /**
     * 系统当前日期的字符串表示，具体格式："yyyy-MM-dd"
     *
     * @return 默认日期按"yyyy-MM-dd"格式显示
     */
    public static String formatDate() {
        return YYYY_MM_DD.format(getCalendar().getTime());
    }

    /**
     * 指定Calendar的字符串表示，具体格式："yyyy-MM-dd"
     *
     * @param cal 指定的日期
     * @return 指定日期按"yyyy-MM-dd"格式显示
     */
    public static String formatDate(Calendar cal) {
        return YYYY_MM_DD.format(cal.getTime());
    }

    /**
     * 指定Date的字符串表示，具体格式："yyyy-MM-dd"
     *
     * @param date 指定的日期
     * @return 指定日期按"yyyy-MM-dd"格式显示
     */
    public static String formatDate(Date date) {
        return YYYY_MM_DD.format(date);
    }

    /**
     * 指定毫秒数表示日期的字符串表示，具体格式："yyyy-MM-dd"
     *
     * @param millis 指定的毫秒数
     * @return 指定毫秒数表示日期按"yyyy-MM-dd"格式显示
     */
    public static String formatDate(long millis) {
        return YYYY_MM_DD.format(new Date(millis));
    }

    /**
     * 系统当前日期按照指定格式的字符串表示
     *
     * @param pattern 指定的格式
     * @return 默认日期按指定格式显示
     */
    public static String formatDate(String pattern) {
        return getSpecifiedDateFormat(pattern).format(getCalendar().getTime());
    }

    /**
     * 指定Calendar按指定格式的字符串表示
     *
     * @param cal     指定的日期
     * @param pattern 指定的格式
     * @return 指定日期按指定格式显示
     */
    public static String formatDate(Calendar cal, String pattern) {
        return getSpecifiedDateFormat(pattern).format(cal.getTime());
    }

    /**
     * 指定Date按指定格式的字符串表示
     *
     * @param date    指定的日期
     * @param pattern 指定的格式
     * @return 指定日期按指定格式显示
     */
    public static String formatDate(Date date, String pattern) {
        return getSpecifiedDateFormat(pattern).format(date);
    }

    /**
     * 系统当前日期的字符串表示，具体格式："yyyy-mm-dd HH:mm:ss"
     *
     * @return 默认日期按"yyyy-mm-dd HH:mm:ss"格式显示
     */
    public static String formatTime() {
        return YYYY_MM_DD_HH_MM_SS.format(getCalendar().getTime());
    }

    /**
     * 指定毫秒数表示日期的字符串表示，具体格式："yyyy-mm-dd HH:mm:ss"
     *
     * @param millis 指定的毫秒数
     * @return 指定毫秒数表示日期按"yyyy-mm-dd HH:mm:ss"格式显示
     */
    public static String formatTime(long millis) {
        return YYYY_MM_DD_HH_MM_SS.format(new Date(millis));
    }

    /**
     * 指定Calendar的字符串表示，具体格式："yyyy-mm-dd HH:mm:ss"
     *
     * @param cal 指定的日期
     * @return 指定Calendar按"yyyy-mm-dd HH:mm:ss"格式显示
     */
    public static String formatTime(Calendar cal) {
        return YYYY_MM_DD_HH_MM_SS.format(cal.getTime());
    }

    /**
     * 指定Date的字符串表示，具体格式："yyyy-mm-dd HH:mm:ss"
     *
     * @param date 指定的日期
     * @return 指定Date按"yyyy-mm-dd HH:mm:ss"格式显示
     */
    public static String formatTime(Date date) {
        return YYYY_MM_DD_HH_MM_SS.format(date);
    }

    /**
     * 系统当前日期的字符串表示，具体格式："HH:mm"
     *
     * @return 默认日期按"HH:mm"格式显示
     */
    public static String formatShortTime() {
        return HH_MM.format(getCalendar().getTime());
    }

    /**
     * 指定毫秒数的字符串表示，具体格式："HH:mm"
     *
     * @param millis 指定的毫秒数
     * @return 指定毫秒数表示日期按"HH:mm"格式显示
     */
    public static String formatShortTime(long millis) {
        return HH_MM.format(new Date(millis));
    }

    /**
     * 指定Calendar的字符串表示，具体格式："HH:mm"
     *
     * @param cal 指定的日期
     * @return 指定日期按"HH:mm"格式显示
     */
    public static String formatShortTime(Calendar cal) {
        return HH_MM.format(cal.getTime());
    }

    /**
     * 指定Date的字符串表示，具体格式："HH:mm"
     *
     * @param date 指定的日期
     * @return 指定日期按"HH:mm"格式显示
     */
    public static String formatShortTime(Date date) {
        return HH_MM.format(date);
    }

    /**
     * 根据指定的格式将字符串转换成Date 如输入：2016-11-06 11:20:20将按照这个转成时间
     *
     * @param src     将要转换的原始字符窜
     * @param pattern 转换的匹配格式
     * @return 如果转换成功则返回转换后的日期
     * @throws ParseException 字符串解析失败抛出异常
     */
    public static Date parseDate(String src, String pattern)
            throws ParseException {
        return getSpecifiedDateFormat(pattern).parse(src);
    }

    /**
     * 根据指定的格式将字符串转换成Calendar 如输入：2016-11-06 11:20:20将按照这个转成时间
     *
     * @param src     将要转换的原始字符窜
     * @param pattern 转换的匹配格式
     * @return 如果转换成功则返回转换后的日期
     * @throws ParseException 字符串解析失败抛出异常
     */
    public static Calendar parseCalendar(String src, String pattern)
            throws ParseException {
        Date date = parseDate(src, pattern);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    /**
     * 根据指定的格式将字符串转换成Timestamp 如输入：2016-11-06 11:20:20将按照这个转成时间
     *
     * @param src     将要转换的原始字符窜
     * @param pattern 转换的匹配格式
     * @return 如果转换成功则返回转换后的时间戳
     * @throws ParseException 字符串解析失败抛出异常
     */
    public static Timestamp parseTimestamp(String src, String pattern)
            throws ParseException {
        Date date = parseDate(src, pattern);
        return new Timestamp(date.getTime());
    }

    /**
     * 日期长度
     */
    private enum DateLength {

        /**
         * 日期格式："yyyy-MM-dd HH:mm:ss", 该格式对应长度：19
         */
        LENGTH_LONG(19, "yyyy-MM-dd HH:mm:ss"),

        /**
         * 日期格式："yyyy-MM-dd HH:mm", 该格式对应长度：16
         */
        LENGTH_MEDIUM(16, "yyyy-MM-dd HH:mm"),

        /**
         * 日期格式："yyyy-MM-dd", 该格式对应长度：10
         */
        LENGTH_SHORT(10, "yyyy-MM-dd"),

        /**
         * 日期格式："HH:mm:ss", 该格式对应长度：8
         */
        LENGTH_SHORTER(8, "HH:mm:ss"),

        /**
         * 日期格式："HH:mm", 该格式对应长度：5
         */
        LENGTH_SHORTEST(5, "HH:mm");

        DateLength(int value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        private final int value;
        private final String desc;

        public int value() {
            return this.value;
        }

        public String getDesc() {
            return this.desc;
        }

        public static String patternOf(int len) {
            for (DateLength dateLength : values()) {
                if (dateLength.value == len) {
                    return dateLength.getDesc();
                }
            }
            throw new IllegalArgumentException("No matching constant for [" + len + "]");
        }
    }
}
