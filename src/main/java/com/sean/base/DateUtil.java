package com.sean.base;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * mclon
 * com.mclon.commons.support.webmvc.utils
 *
 * @author zhaozhenhua
 * 2018/8/15 0015
 * 16:10
 */


public class DateUtil {

    public static final String timeFomart = "yyyy-MM-dd HH:mm:ss";

    public static final String dateFormart = "yyyy-MM-dd";


    /**
     * 获取当前时间字符串 年月日时分秒
     */
    public static String getNowDateYmdhmsString() {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat(timeFomart);
        return df.format(date);
    }

    /**
     * 获取当前时间字符串 年月日
     */
    public static String getNowDateYmdString() {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat(dateFormart);
        return df.format(date);
    }

    /**
     * 获取当前时间date 年月日时分秒
     */
    public static Date getNowDateYmdhmsDate() {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat(timeFomart);
        String strDate = df.format(date);
        Date returnDate = null;
        try {
            returnDate = df.parse(strDate);
        } catch (Exception e) {

        }
        return returnDate;
    }

    /**
     * 获取当前时间date 年月日
     */
    public static Date getNowDateYmdDate() {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat(dateFormart);
        String strDate = df.format(date);
        Date returnDate = null;
        try {
            returnDate = df.parse(strDate);
        } catch (Exception e) {

        }
        return returnDate;
    }

    /**
     * 通过入参 (字符串) 格式化  年月日时分秒
     */
    public static String getDateYmdhmsByString(String date) {
        SimpleDateFormat df = new SimpleDateFormat(timeFomart);
        return df.format(date);
    }

    /**
     * 通过入参 (字符串) 格式化  年月日
     */
    public static String getDateYmdByString(String date) {
        SimpleDateFormat df = new SimpleDateFormat(dateFormart);
        return df.format(date);
    }


    /**
     * 通过入参 (字符串) 格式化  年月日时分秒
     */
    public static Date getDateYmdhmsByDate(Date date) {
        SimpleDateFormat df = new SimpleDateFormat(timeFomart);
        String strDate = df.format(date);
        Date returnDate = null;
        try {
            returnDate = df.parse(strDate);
        } catch (Exception e) {

        }
        return returnDate;
    }

    /**
     * 通过入参 (字符串) 格式化  年月日
     */
    public static Date getDateYmdByDate(Date date) {
        SimpleDateFormat df = new SimpleDateFormat(dateFormart);
        String strDate = df.format(date);
        Date returnDate = null;
        try {
            returnDate = df.parse(strDate);
        } catch (Exception e) {

        }
        return returnDate;
    }

    /**
     * 通过入参 (字符串) 格式化  年月日
     */
    public static Date getDateYmdhmsByDate(String date) {
        SimpleDateFormat df = new SimpleDateFormat(timeFomart);
        String strDate = df.format(date);
        Date returnDate = null;
        try {
            returnDate = df.parse(strDate);
        } catch (Exception e) {

        }
        return returnDate;
    }

    /**
     * 通过入参 (字符串) 格式化  年月日
     */
    public static Date getDateYmdByDate(String date) {
        SimpleDateFormat df = new SimpleDateFormat(dateFormart);
        String strDate = df.format(date);
        Date returnDate = null;
        try {
            returnDate = df.parse(strDate);
        } catch (Exception e) {

        }
        return returnDate;
    }


    /**
     * 获取当前时间date 年月日时分秒
     */
    public static Date getDateYmdhmsDate(String dateStr) {
        SimpleDateFormat df = new SimpleDateFormat(timeFomart);
        Date returnDate = null;
        try {
            returnDate = df.parse(dateStr);
        } catch (Exception e) {

        }
        return returnDate;
    }

    /**
     * 获取当前时间date 年月日
     */
    public static Date getDateYmdDate(String dateStr) {
        SimpleDateFormat df = new SimpleDateFormat(dateFormart);
        Date returnDate = null;
        try {
            returnDate = df.parse(dateStr);
        } catch (Exception e) {

        }
        return returnDate;
    }

    /**
     * 通过入参 (字符串) 格式化  年月日时分秒
     */
    public static String getStringYmdByDate(Date date) {
        SimpleDateFormat df = new SimpleDateFormat(dateFormart);
        String strDate = "";
        try {
            strDate = df.format(date);
        } catch (Exception e) {
        }
        return strDate;
    }

}
