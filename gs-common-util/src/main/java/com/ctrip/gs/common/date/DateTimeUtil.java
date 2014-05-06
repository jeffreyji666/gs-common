package com.ctrip.gs.common.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

/**
 * 时间处理帮助类。
 * 
 * @author wgji
 */
public final class DateTimeUtil {

    public static final long MILLISECONDS_MINUTE = 60000;
    public static final long MILLISECONDS_HOUR = 3600000;
    public static final long MILLISECONDS_DAY = 86400000;
    public static final long MILLISECONDS_WEEK = 604800000;

    // 2010-01-01
    public static final int FMT_DATE_YYYY_MM_DD = 1;
    // 20100101
    public static final int FMT_DATE_YYYYMMDD = 2;
    // 100101
    public static final int FMT_DATE_YYMMDD = 3;
    // 2010
    public static final int FMT_DATE_YYYY = 4;
    // 1001
    public static final int FMT_DATE_YYMM = 5;
    // 201011
    public static final int FMT_DATE_YYYYMM = 6;
    // 2010-11-02 16:27:30
    public static final int FMT_DATE_YYYY_MM_DD_HH_MM_SS = 7;
    // 201011021623
    public static final int FMT_DATE_YYYYMMDDHHMM = 8;
    // 20101205144039
    public static final int FMT_DATE_YYYYMMDDHHMMSS = 9;
    // 20101205144039
    public static final int FMT_DATE_YYYY_MM_DD_HH_MM = 10;
    // 10-11-02 16:27:30
    public static final int FMT_DATE_YY_MM_DD_HH_MM_SS = 11;
    // 2010/11
    public static final int FMT_DATE_YYYY_SLASH_MM = 12;

    private DateTimeUtil() {
    }

    /**
     * Convert "yyyy-MM-dd" formatted string date into date object.
     * 
     * @param dateStr
     * @return
     * @throws ParseException
     */
    public static Date convertDate(String dateStr) {
        return getDateByString(dateStr, FMT_DATE_YYYY_MM_DD);
    }

    /**
     * Format the date into "yyyy-MM-dd" format.
     * 
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        return formatDate(date, FMT_DATE_YYYY_MM_DD);
    }

    /**
     * 判断时间是否是今天 是今天时间，返回true 非今天时间，返回false
     * 
     * @param date
     * @return
     */
    public static boolean dateIsToday(Date date) {
        if (date == null) {
            return false;
        }

        return formatDate(date, FMT_DATE_YYYY_MM_DD).equals(formatDate(new Date(), FMT_DATE_YYYY_MM_DD));
    }

    /**
     * Get the Chinese representation of date， e.g. 2010年11月11日
     * 
     * @param date
     * @return
     */
    public static String getChineseDate(Date date) {
        return getChineseDate(formatDate(date, FMT_DATE_YYYY_MM_DD));
    }

    /**
     * Format date into a string
     * 
     * @param date
     *            the date to be formatted
     * @param nFmt
     *            FMT_DATE_XXXX constant
     * @return the formatted string date.
     */
    public static String formatDate(Date date, int nFmt) {
        if (date == null) {
            return "";
        }

        SimpleDateFormat fmtDate = getDateFormat(nFmt);
        try {
            return fmtDate.format(date);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Parse string date into Date object.
     * 
     * @param strDate
     *            the string to be parsed
     * @param nFmt
     *            FMT_DATE_XXXX constant
     * @return the parsed date.
     */
    public static Date getDateByString(String strDate, int nFmt) {
        if (StringUtils.isEmpty(strDate)) {
            return null;

        }
        SimpleDateFormat fmtDate = getDateFormat(nFmt);
        try {
            return fmtDate.parse(strDate);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Gets the current date and time in a Date object.
     * 
     * @return
     */
    public static Date getCurrentDate() {
        return new java.util.Date();
    }

    /**
     * Get the current date day, without time
     * 
     * @return
     */
    public static Date getCurrentDateDay() {
        return getDateDay(new java.util.Date());
    }

    /**
     * Get the day and remove time from the specific time
     * 
     * @param date
     * @return
     */
    public static Date getDateDay(Date date) {
        return DateUtils.truncate(date, Calendar.DATE);
    }

    /**
     * Subtracts weeks from the given Date
     * 
     * @param d
     * @param weeks
     * @return
     */
    public static Date subtractWeeks(Date d, int weeks) {
        return DateUtils.addWeeks(d, -weeks);
    }

    /**
     * Subtracts days from the given Date
     * 
     * @param d
     * @param days
     * @return
     */
    public static Date subtractDays(Date d, int days) {
        return DateUtils.addDays(d, -days);
    }

    /**
     * Plus days from the given Date
     * 
     * @param d
     * @param days
     * @return
     */
    public static Date plusDays(Date d, int days) {
        return DateUtils.addDays(d, days);
    }

    /**
     * Plus months from the given Date and month amount.
     * 
     * @param date
     * @param amount
     * @return
     */
    public static Date plusMonths(Date date, int amount) {
        return DateUtils.addMonths(date, amount);
    }

    /**
     * Gets the difference in days between the given dates.
     * 
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getDaysDiff(Date startDate, Date endDate) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);

        return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
    }

    /**
     * 返回昨天日期
     * 
     * @return
     */
    public static Date getYestoday() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    /**
     * 返回某月第一天
     * 
     * @param year
     * @param month
     * @return
     */
    public static String getFirstDayOfMonth(int year, int month) {
        int m = month;
        if (m > 12) {
            m = 12;
        }
        if (m < 1) {
            m = 1;
        }

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, m - 1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));
        return new SimpleDateFormat("yyyy-MM-dd ").format(cal.getTime());
    }

    /**
     * 返回某月最后一天
     * 
     * @param year
     * @param month
     * @return
     */
    public static String getLastDayOfMonth(int year, int month) {
        int m = month;
        if (m > 12) {
            m = 12;
        }
        if (m < 1) {
            m = 1;
        }

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, m - 1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
        return new SimpleDateFormat("yyyy-MM-dd ").format(cal.getTime());
    }

    /**
     * 返回当月第一天
     * 
     * @return
     */
    public static String getFirstDayOfCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return new SimpleDateFormat("yyyy-MM-dd ").format(cal.getTime());
    }

    /**
     * 判断是否是yyyy-MM-dd格式
     * 
     * @return
     */
    public static boolean isNormalFormat(String strDate) {
        SimpleDateFormat fmtDate = getDateFormat(FMT_DATE_YYYY_MM_DD);
        Date date = null;
        try {
            date = fmtDate.parse(strDate);
        } catch (Exception e) {
        }
        return date != null;
    }

    /**
     * 
     * @return current time of milliseconds
     */
    public static long currentTimeMillis() {
    	return System.currentTimeMillis();
    }

    private static String getChineseDate(String strDate) {
        if (strDate == null || strDate.length() < 10) {
            return "";
        }
        return strDate.substring(0, 4) + "\u5E74" + strDate.substring(5, 7) + "\u6708" + strDate.substring(8, 10)
                + "\u65E5";
    }

    private static SimpleDateFormat getDateFormat(int nFmt) {
        SimpleDateFormat fmtDate;
        switch (nFmt) {
        case FMT_DATE_YYYY_MM_DD:
            fmtDate = new SimpleDateFormat("yyyy-MM-dd");
            break;
        case FMT_DATE_YYYYMMDD:
            fmtDate = new SimpleDateFormat("yyyyMMdd");
            break;
        case FMT_DATE_YYMMDD:
            fmtDate = new SimpleDateFormat("yyMMdd");
            break;
        case FMT_DATE_YYYY:
            fmtDate = new SimpleDateFormat("yyyy");
            break;
        case FMT_DATE_YYMM:
            fmtDate = new SimpleDateFormat("yyMM");
            break;
        case FMT_DATE_YYYYMM:
            fmtDate = new SimpleDateFormat("yyyyMM");
            break;
        case FMT_DATE_YYYY_MM_DD_HH_MM_SS:
            fmtDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            break;
        case FMT_DATE_YYYYMMDDHHMM:
            fmtDate = new SimpleDateFormat("yyyyMMddHHmm");
            break;
        case FMT_DATE_YYYYMMDDHHMMSS:
            fmtDate = new SimpleDateFormat("yyyyMMddHHmmss");
            break;
        case FMT_DATE_YYYY_MM_DD_HH_MM:
            fmtDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            break;
        case FMT_DATE_YY_MM_DD_HH_MM_SS:
            fmtDate = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
            break;
        case FMT_DATE_YYYY_SLASH_MM:
            fmtDate = new SimpleDateFormat("yyyy/MM");
            break;
        default:
            fmtDate = new SimpleDateFormat("yyyy-MM-dd");
            break;
        }
        return fmtDate;
    }
}
