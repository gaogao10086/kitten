package com.gys.kitten.core.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Author: kitten
 * Date: 13-9-1
 * Time: 下午4:17
 * Des: 标准数据格式化工具
 */

public class DateUtil {

    public static Date parseString2Date(String d) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.parse(d);
    }

    public static Date parseString2Date2(String d) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(d);
    }

    public static String parseDate2String(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(d);
    }

    public static String parseDate2String2(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(d);
    }

    public static int getWeekOfYear(String d) {
        DateTimeFormatter formatter = ISODateTimeFormat.basicDate();
        DateTime dateTimeA = formatter.parseDateTime(d);
        int week = Integer.valueOf(dateTimeA.getWeekOfWeekyear());
        return week;
    }

    /**
     * @param year
     * @param week
     * @return
     */
    public static Date getWeekLastDayOfYear(int year, int week) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.WEEK_OF_YEAR, week);
        int min = calendar.getActualMinimum(Calendar.DAY_OF_WEEK) + 1; // 获取周开始基准
        int current = calendar.get(Calendar.DAY_OF_WEEK); // 获取当天周内天数
        calendar.add(Calendar.DAY_OF_WEEK, min - current); // 当天-基准，获取周开始日期
        Date start = calendar.getTime();
        calendar.add(Calendar.DAY_OF_WEEK, 6); // 开始+6，获取周结束日期
        Date end = calendar.getTime();
        return end;
    }

    public static Date getMonthLastDayOfYear(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date lastDate = cal.getTime();
        return lastDate;
    }

    public static List<String> getLoopDate(String timeA, String timeB) {
        List<String> datelist = new ArrayList<String>();
        SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");

        Date dateBegin = null;
        Date dateEnd = null;
        try {
            dateBegin = formater.parse(timeA);
            dateEnd = formater.parse(timeB);
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        int c = 0;
        Calendar ca = Calendar.getInstance();
        while (dateBegin.compareTo(dateEnd) < 0) {
            ca.setTime(dateBegin);
            if (c == 0) {
                ca.add(Calendar.DATE, 0);// 把dateBegin加上1天然后重新赋值给date1
            } else {
                ca.add(Calendar.DATE, 1);
            }
            dateBegin = ca.getTime();
            datelist.add(formater.format(dateBegin));
            c++;
        }
        return datelist;
    }


    public static void main(String[] args) {
        long time = 1393775106684l;
        Date nima = new Date(time);
        DateFormat df = new SimpleDateFormat("HH:mm");

        String hehe = df.format(nima);
        int hour = Integer.parseInt(hehe.substring(0, 2));
        int min = Integer.parseInt(hehe.substring(3, 5));
        System.out.println(hour + "");
        System.out.println(min + "");
        Integer flag = 0;
        for (int i = 0; i <= 23; i++) {
            if (hour == i) {
                if (min < 30) {
                    flag = i * 2;
                } else {
                    flag = i * 2 + 1;
                }
                break;
            }
        }
        System.out.println(flag.toString());
    }
}
