package com.hailas.common.utils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 时间工具类
 * 
 * @author ruoyi
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils
{
    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    
    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", 
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 获取当前Date型日期
     * 
     * @return Date() 当前日期
     */
    public static Date getNowDate()
    {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     * 
     * @return String
     */
    public static String getDate()
    {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime()
    {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow()
    {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format)
    {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date)
    {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr(final String format, final Date date)
    {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String ts)
    {
        try
        {
            return new SimpleDateFormat(format).parse(ts);
        }
        catch (ParseException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath()
    {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime()
    {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str)
    {
        if (str == null)
        {
            return null;
        }
        try
        {
            return parseDate(str.toString(), parsePatterns);
        }
        catch (ParseException e)
        {
            return null;
        }
    }
    
    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate()
    {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算两个时间差
     */
    public static String getDatePoor(Date endDate, Date nowDate)
    {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }


    /**
     * 获取某天的开始时间即00:00
     * @param date
     * @return
     */
    public static Date getStartTimeOfDay(Date date){
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.HOUR_OF_DAY,0);
        now.set(Calendar.MINUTE,0);
        now.set(Calendar.SECOND,0);
        now.set(Calendar.MILLISECOND,0);
        return now.getTime();
    }

    public static Date getEndTimeOfDay(Date date){
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.add(Calendar.DAY_OF_MONTH,1);
        now.set(Calendar.HOUR_OF_DAY,0);
        now.set(Calendar.MINUTE,0);
        now.set(Calendar.SECOND,0);
        now.set(Calendar.MILLISECOND,0);
        return now.getTime();
    }

    /**
     * 对用户友好的表现形式
     * @param date
     * @return
     */
    public static String getFriendlyFormat(Date date){
        Date now = new Date();
        long diff = now.getTime() - date.getTime();
        /*if (diff<0){
            throw new IllegalArgumentException("the argument 'date' mast before now");
        }*/
        diff = diff%60000==0?diff/60000:diff/60000+1;
        if (diff == 0){
            return "刚刚";
        }else if (diff < 30){
            return diff + "分钟前";
        }else if (diff<60){
            return "半小时前";
        }/*else if (diff<24*60){
            return (diff/60)+"小时前";
        }else if (diff<7*24*60){
            return (diff/(24*60))+"天前";
        }else if (diff<31*24*60){
            return diff/(7*24*60)+"周前";
        }else if (diff<365*24*60){
            return diff/(31*24*60)+"个月前";
        }*/else {
            //return diff/(365*24*60)+"年前";
            return format(date,"yyyy-MM-dd HH:mm");
        }
    }

    /**
     * 获取n天前的时间
     * @param n
     * @return
     */
    public static Date getTimeNDaysBefore(int n){
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DAY_OF_MONTH,n*-1);
        return now.getTime();
    }

    /**
     * 获取n天后的时间
     * @param n
     * @return
     */
    public static Date getTimeNDaysAfter(int n){
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DAY_OF_MONTH,n);
        return now.getTime();
    }

    /**
     * 获取n天后的时间
     * @param n
     * @return
     */
    public static Date getTimeNDaysAfter(Date date,int n){
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.add(Calendar.DAY_OF_MONTH,n);
        return now.getTime();
    }

    /**
     * 昨天
     * @return
     */
    public static Date yesterday(){
        return getTimeNDaysBefore(1);
    }

    /**
     * 明天
     * @return
     */
    public static Date tomorrow(){
        return getTimeNDaysBefore(-1);
    }

    /**
     * 根据月份中的日获取日期
     * @param day
     * @return
     */
    public static Date getDateBaseDay(int day){
        Calendar now = Calendar.getInstance();
        now.set(Calendar.DAY_OF_MONTH,day-1);
        now.set(Calendar.HOUR_OF_DAY,0);
        now.set(Calendar.MINUTE,0);
        now.set(Calendar.SECOND,0);
        return now.getTime();
    }

    /**
     * 获取本月第一天
     * @return
     */
    public static Date getFirstDayOfMonth(){
        Calendar now = Calendar.getInstance();
        now.set(Calendar.DAY_OF_MONTH,1);
        now.set(Calendar.HOUR_OF_DAY,0);
        now.set(Calendar.MINUTE,0);
        now.set(Calendar.SECOND,0);
        return now.getTime();
    }

    /**
     * 获取上月第一天
     * @return
     */
    public static Date getFirstDayOfPreMonth(){
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MONTH,-1);
        now.set(Calendar.DAY_OF_MONTH,1);
        now.set(Calendar.HOUR_OF_DAY,0);
        now.set(Calendar.MINUTE,0);
        now.set(Calendar.SECOND,0);
        return now.getTime();
    }

    /**
     * 获取本周第一天
     * @return
     */
    public static Date getFirstDayOfWeek(){
        Calendar now = Calendar.getInstance();
        now.set(Calendar.DAY_OF_WEEK,2);
        now.set(Calendar.HOUR_OF_DAY,0);
        now.set(Calendar.MINUTE,0);
        now.set(Calendar.SECOND,0);
        return now.getTime();
    }

    /**
     * 获取上周第一天
     * @return
     */
    public static Date getFirstDayOfPreWeek(){
        Calendar now = Calendar.getInstance();
        now.add(Calendar.WEEK_OF_MONTH,-1);
        now.set(Calendar.DAY_OF_WEEK,2);
        now.set(Calendar.HOUR_OF_DAY,0);
        now.set(Calendar.MINUTE,0);
        now.set(Calendar.SECOND,0);
        return now.getTime();
    }

    /**
     * 根据指定格式格式化日期
     * @param time
     * @param pattern
     * @return
     */
    public static String format(Date time,String pattern){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(time);
    }

    /**
     * 根据给定格式将字符串转换为日期
     * @param time
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static Date parse(String time, String pattern) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.parse(time);
    }

    /**
     * 将日期转换到指定时区
     * @param sourceDate
     * @param targetTimeZone
     * @return
     */
    public static Date transformToTimeZone(Date sourceDate, TimeZone targetTimeZone) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(targetTimeZone);
        calendar.setTime(sourceDate);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
        return calendar2.getTime();
    }

    /**
     * 根据出生日期计算年龄
     * @param birthDay
     * @return
     * @throws Exception
     */
    public static int getAge(Date birthDay) {

        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            return 0;
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                age--;
            }
        }

        return age;
    }

    public static Date geLastWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekMonday(date));
        cal.add(Calendar.DATE, -7);
        return cal.getTime();
    }

    public static int compareTime(Date date) {
        int result = -1;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Calendar nowCal = Calendar.getInstance();
        if(cal.before(nowCal)){
            //传入时间小于当前时间
            result = -1;
        }else if(cal.after(nowCal)){
            //传入时间大于当前时间
            result = 1;
        }else if(cal.equals(nowCal)){
            //传入时间等于当前时间
            result = 0;
        }
        return  result;
    }

    public static Date getThisWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        return cal.getTime();
    }

    public static Date getNextWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekMonday(date));
        cal.add(Calendar.DATE, 7);
        return cal.getTime();
    }

    public static String getTime(long m) {
        if(m < 60) {//秒
            return NumFormat(0) + ":" + NumFormat(m);
        }

        if(m < 3600) {//分
            return NumFormat(m / 60) + ":" + NumFormat(m % 60);
        }

        if(m < 3600 * 24) {//时
            return NumFormat(m / 60 / 60) + ":" + NumFormat(m / 60 % 60) + ":" + NumFormat(m % 60);
        }

        if(m >= 3600 * 24) {//天
            return NumFormat(m / 60 / 60 /24) + "天" +NumFormat(m / 60 / 60 % 24) + ":" + NumFormat(m / 60 % 60) + ":" + NumFormat(m % 60);
        }

        return "--";
    }

    /**
     * 毫秒转化为天小时分钟秒
     *
     * @param ms 毫秒值
     * @return
     */
    public static String formatTime(long ms) {
        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh)/ mi;
        //double second = (ms - day * dd - hour * hh - minute * mi) * 1.0 / ss;
        // long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        if (day > 0) {
            return day + "天" + hour + "小时" + minute + "分钟";
        } else if (hour > 0) {
            return hour + "小时" + minute + "分钟";
        } else if (minute > 0) {
            return minute + "分钟";
        } else {
            return 0 + "分钟";
        }
    }

    public static String NumFormat(long i) {
        if (String.valueOf(i).length() < 2) {
            return "0" + i;
        } else {
            return String.valueOf(i);
        }
    }

    /**
     * 获取两日期相差月份
     * @param date1
     * @param date2
     * @return
     * @throws ParseException
     */
    public static int getMonthSpace(Date date1, Date date2) {
        Calendar bef = Calendar.getInstance();
        Calendar aft = Calendar.getInstance();
        bef.setTime(date1);
        aft.setTime(date2);
        int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
        int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
        return result+month;

    }
}
