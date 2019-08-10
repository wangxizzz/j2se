import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wxi.wang
 * <p>
 * 2019/7/27 10:49
 * Decription:
 */
public class DateUtil {
    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

    // DateTimeFormatter是线程安全的，于是全局共用
    //变量名采用格式字母的头字母组合
    private static final DateTimeFormatter hmfmt = DateTimeFormat.forPattern("HH:mm");
    private static final DateTimeFormatter hmfmtcn = DateTimeFormat.forPattern("HH点mm分");
    private static final DateTimeFormatter ymdfmt = DateTimeFormat.forPattern("yyyy-MM-dd");
    private static final DateTimeFormatter ymd = DateTimeFormat.forPattern("yyyyMMdd");
    private static final DateTimeFormatter ymfmt = DateTimeFormat.forPattern("yyyy-MM");
    private static final DateTimeFormatter ymdchinesefmt = DateTimeFormat.forPattern("yyyy年MM月dd日");
    private static final DateTimeFormatter mdchinesefmt = DateTimeFormat.forPattern("MM月dd日");
    private static final DateTimeFormatter ymdhmsfmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter hmsfmt = DateTimeFormat.forPattern("HH:mm:ss");
    private static final DateTimeFormatter ymdhmfmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter mdhmchinesefmt = DateTimeFormat.forPattern("MM月dd日HH时mm分");
    private static final DateTimeFormatter ymdhmchfmt = DateTimeFormat.forPattern("yyyy年MM月dd日HH:mm");
    private static final DateTimeFormatter mdhmfmt = DateTimeFormat.forPattern("MM-dd HH:mm");
    private static final DateTimeFormatter mdhmfmtcn = DateTimeFormat.forPattern("MM月dd日 HH:mm分");
    private static final DateTimeFormatter ymdhmslinkedfmt = DateTimeFormat.forPattern("yyyy-MM-dd-HH:mm:ss");
    private static final DateTimeFormatter ymdhmsfmtNew = DateTimeFormat.forPattern("yyyyMMddHHmmss");

    public static final List<String> TIME_HOUR = new ArrayList<String>();
    static{
        for(int i=0;i<10;i++){
            TIME_HOUR.add("0"+i);
        }
        for(int i=10;i<24;i++){
            TIME_HOUR.add(String.valueOf(i));
        }
    }

    public static final List<String> TIME_MINUTE = new ArrayList<String>();
    static{
        for(int i=0;i<10;i++){
            TIME_MINUTE.add("0"+i);
        }
        for(int i=10;i<60;i++){
            TIME_MINUTE.add(String.valueOf(i));
        }
    }


    private static final Timestamp lowestPGTime;
    private static final Timestamp highestPGTime;
    static{
        Timestamp ts=null;
        try{
            ts=new Timestamp(ymdfmt.parseMillis("1970-01-01"));
        }catch(Exception e){
            ts=null;
        }
        lowestPGTime=ts;
        try{
            ts=new Timestamp(ymdfmt.parseMillis("2222-01-01"));
        }catch(Exception e){
            ts=null;
        }
        highestPGTime=ts;
    }
    public static Timestamp getLowestTime(){
        return lowestPGTime;
    }
    public static Timestamp getHighestTime(){
        return highestPGTime;
    }


    /*************1、从字符串获得Date、Timestamp或者Time对象***********************/
    /**
     * 处理这种字符串格式：yyyy-MM-dd
     * @param tsStr
     * @return
     */
    public static Timestamp getTimestampFromShortString(String tsStr){
        return getTimeStampByFormat(tsStr, "yyyy-MM-dd");
    }

    /**
     * 处理这种形式字符串：yyyy-MM-dd
     *
     * @param tsStr
     * @return
     */
    public static Timestamp getTimeStamp(String tsStr) {
        return getTimeStampByFormat(tsStr, "yyyy-MM-dd");
    }

    /**
     * 处理这种形式字符串：yyyy-MM-dd
     * @param s
     * @return
     */
    public static Timestamp getPGTimeStampFromString(String s){
        return getTimeStampByFormat(s, "yyyy-MM-dd");
    }



    /**
     * 处理这种形式字符串：yyyy-MM-dd HH:mm
     * @param s
     * @return
     */
    public static Timestamp getTimeStampNoSecFromString(String s) {
        return getTimeStampByFormat(s, "yyyy-MM-dd HH:mm");
    }

    /**
     * 处理这种形式字符串：yyyy-MM-dd HH:mm:ss
     * @param s
     * @return
     */
    public static Timestamp getPGDetailImeStampFromString(String s) {
        return getTimeStampByFormat(s, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 处理这种形式字符串：yyyy-MM-dd HH:mm:ss
     * @param s
     * @return
     */
    public static Timestamp getTimeStampFromString(String s) {
        return getTimeStampByFormat(s, "yyyyMMddHHmmss");
    }

    public static DateTime getDatetimeFromString(String s) {
        return DateTime.parse(s, ymdhmsfmt);
    }


    public static Timestamp getPayCenterSheetTimeStampFromString(String s) {
        return getTimeStampByFormat(s, "yyyy/MM/dd HH:mm:ss");
    }

    /**
     * 注意：tsStr格式需要保持与formatString一致
     * 比如tsStr是”2013-01-03 12：11：12“，formatString是yyyy-MM-dd，
     * 那将会报异常，返回null
     *
     * added by ganbao. 2011-10-24
     *
     * @param tsStr
     * @param formatString
     * @return
     */
    public static Timestamp getTimeStampByFormat(String tsStr, String formatString) {
        if(tsStr == null || tsStr.trim().isEmpty()) return null;
        try {
            tsStr = tsStr.trim();
            return new Timestamp(DateTimeFormat.forPattern(formatString).withZoneUTC().parseMillis(tsStr));
        } catch (Exception e) {
            logger.warn("parse timestamp error", e);
            return null;
        }
    }

    public static boolean hasCrossTime(Timestamp startTime1,Timestamp endTime1,
                                       Timestamp startTime2,Timestamp endTime2) {
        if (endTime1.before(startTime1)){
            throw new RuntimeException("比较的结束时间不能在开始时间之前");
        }
        if (endTime2.before(startTime2)){
            throw new RuntimeException("比较的结束时间不能在开始时间之前");
        }
        if (!startTime1.before(startTime2) && !startTime1.after(endTime2)){
            return true;
        }
        if (!endTime1.before(startTime2) && !endTime1.after(endTime2)){
            return true;
        }
        return false;
    }

    public static Date convertDate(String formatString, String dateValue) {
        if (dateValue == null || dateValue.trim().isEmpty() || dateValue.indexOf("null") >= 0){
            return null;
        }
        try {
            dateValue = dateValue.trim();
            return DateTimeFormat.forPattern(formatString).withZoneUTC().parseDateTime(dateValue).toDate();
        } catch (Exception e) {
            logger.error("parse date error", e);
            return null;
        }
    }

    public static Date convertDate(String dateValue) {
        if (dateValue.indexOf(":") > 0) {
            return convertDate("yyyy-MM-dd HH:mm", dateValue);
        } else {
            return parseDate(dateValue);
        }
    }


    /**
     * 处理这种形式字符串：yyyy-MM-dd
     * @param value
     * @return
     */
    public static Date parseDate(String value) {
        try {
            return LocalDate.parse(value).toDate();
        } catch (Exception e) {
            logger.error("parse Date error! value:{}", value, e);
            return null;
        }
    }

    /**
     * 如果后一个时间小于前一个时间，就是跨天。
     * @param firstTime
     * @param secondTime
     * @return
     */
    public static boolean isCrossDay(String firstTime,String secondTime){
        if(StringUtils.isEmpty(firstTime)||StringUtils.isEmpty(secondTime)){
            return false;
        }
        try{
            long firstTimeMillis = hmfmt.parseMillis(firstTime.trim());
            long secondTimeMillis = hmfmt.parseMillis(secondTime.trim());
            if(firstTimeMillis > secondTimeMillis){
                return true;
            }
        }catch(Exception e){
            logger.warn("parse timestamp error", e);
            return false;
        }
        return false;
    }

    /**
     * 打印出HH点mm分
     * @param ts
     * @return
     */
    public static String getHourMinuteStr(Timestamp ts){
        if (ts == null) return "";
        return hmfmtcn.print(ts.getTime());
    }

    public static String getHourMinuteStr(Date dt){
        if (dt == null) return "";
        return hmfmtcn.print(dt.getTime());
    }


    /*************2、从Date或者Timestamp对象获得字符串表示***********************/
    /**
     * 返回格式：yyyy年MM月dd日
     * @param dt
     * @return
     */
    public static String getYearMonthDayStr(Date dt){
        if (dt == null) return "";
        return ymdchinesefmt.print(dt.getTime());
    }

    public static String getHMSStr(Date dt){
        if (dt == null) return "";
        return hmsfmt.print(dt.getTime());
    }
    /**
     * 返回格式：MM月dd日
     * @param ts
     * @return
     */
    public static String getMonthDayStr(Timestamp ts){
        if (ts == null) return "";
        return mdchinesefmt.print(ts.getTime());
    }
    /**
     * 返回格式：MM月dd日
     * @param dt
     * @return
     */
    public static String getMonthDayStr(Date dt){
        if (dt == null) return "";
        return mdchinesefmt.print(dt.getTime());
    }

    /**
     * 返回格式：MM月dd日HH时mm分
     * @param ts
     * @return
     */
    public static String getChineseTimeString(Timestamp ts) {
        if (ts == null) return "";
        return mdhmchinesefmt.print(ts.getTime());
    }

    /**
     * 返回格式：yyyy-MM-dd HH:mm:ss
     * @param ts
     * @return
     */
    public static String getTimeStringFromTimestamp(Timestamp ts){
        if(ts == null) return "";
        return ymdhmsfmt.print(ts.getTime());
    }

    /**
     * 返回格式：yyyy-MM-dd HH:mm:ss
     * @param dt
     * @return
     */
    public static String formatDateTime(Date dt) {
        if(dt == null) return "";
        return ymdhmsfmt.print(dt.getTime());
    }


    /**
     * 返回格式：yyyy-MM-dd
     * @param dt
     * @return
     */
    public static String formatDate(Date dt) {
        if(dt == null) return "";
        return ymdfmt.print(dt.getTime());
    }


    /**************3、处理日期各个字段字段************/
    public static Date addDays(Date date, int days){
        return new DateTime(date).plusDays(days).toDate();
    }

    public static Timestamp addDays(Timestamp ts, int days) {
        return new Timestamp(new DateTime(ts.getTime()).plusDays(days).getMillis());
    }

    public static Timestamp deleteDays(Timestamp ts, int days) {
        return new Timestamp(new DateTime(ts.getTime()).minusDays(days).getMillis());
    }

    public static Timestamp addMinutes(Timestamp ts, int minutes) {
        return new Timestamp(new DateTime(ts.getTime()).plusMinutes(minutes).getMillis());
    }

    public static Timestamp addHours(Timestamp ts, int hours) {
        return new Timestamp(new DateTime(ts.getTime()).plusHours(hours).getMillis());
    }

    public static Timestamp addSeconds(Timestamp ts, int seconds){
        return new Timestamp(new DateTime(ts.getTime()).plusSeconds(seconds).getMillis());
    }

    public static Date addDay(Date dt, int day) {
        return new DateTime(dt).plusDays(day).toDate();
    }


    public static Date addMonth(Date date,int month){
        return new DateTime(date).plusMonths(month).toDate();
    }

    public static Timestamp addMonth(Timestamp ts,int month){
        return new Timestamp(new DateTime(ts.getTime()).plusMonths(month).getMillis());
    }

    public static int getHour(Time time){
        if(time==null) return 0;
        return new DateTime(time).getHourOfDay();
    }

    public static int getMinute(Time time){
        if(time==null) return 0;
        return new DateTime(time).getMinuteOfHour();
    }


    private final static long SEC_IN_A_DAY=24L*3600*1000;
    public static long diffDate(Date date1, Date date2){
        return (date1.getTime()-date2.getTime()) / SEC_IN_A_DAY;
    }

    public static long diffDate(Timestamp ts1,Timestamp ts2){
        return (ts1.getTime()-ts2.getTime()) / SEC_IN_A_DAY;
    }


    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(new DateTime().getMillis());
    }
    public static Date getCurrentDate() {
        return new DateTime().withTime(0, 0, 0, 0).toDate();
    }


    /**
     * 得到long所对应的date,这个date的小时，分钟，秒都是0
     * @param currentTime
     * @return
     */
    public static Date getFirstTimeOfDay(long currentTime) {
        return new DateTime(currentTime).withTime(0, 0, 0, 0).toDate();
    }

    public static Date getLastTimeOfDay(long currentTime) {
        return new DateTime(currentTime).withTime(23, 59, 59, 0).toDate();
    }

    public static Date getTimeOfDay(long currentTime, int hour, int minute, int seconds, int millis) {
        return new DateTime(currentTime).withTime(hour, minute, seconds, millis).toDate();
    }

    public static Timestamp getFirstMsOfThisDay(Timestamp ts){
        return new Timestamp(new DateTime(ts.getTime()).withTime(0, 0, 0, 0).getMillis());
    }

    public static Date getFirstMsOfThisDay(Date date){
        return new DateTime(date).withTime(0, 0, 0, 0).toDate();
    }


    public static Timestamp getLastMsOfThisDay(Timestamp ts){
        return new Timestamp(getLastMsOfThisDay(ts.getTime()));
    }

    public static Date getLastMsOfThisDay(Date date){
        return new Date(getLastMsOfThisDay(date.getTime()));
    }

    private static long getLastMsOfThisDay(long time){
        return new DateTime(time).withTime(0, 0, 0, 0).getMillis() + SEC_IN_A_DAY -1L;
    }
}
