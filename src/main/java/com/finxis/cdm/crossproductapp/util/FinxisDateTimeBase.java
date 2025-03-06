package com.finxis.cdm.crossproductapp.util;

import com.finxis.cdm.crossproductapp.util.TimeZoneMap;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FinxisDateTimeBase {


    public String shortDate;
    public String longDateTime;
    public String year;
    public String month;
    public String day;
    public String time;
    public String hour;
    public String min;
    public String sec;
    public String msec;
    public String timezone;
    public ZonedDateTime zdtWithZoneOffset;
    public ZonedDateTime zdtInLocalTimeline;
    public DateTimeFormatter shortDateFormat = DateTimeFormatter.ISO_LOCAL_DATE;
    public DateTimeFormatter shortDateWithTzFormat = DateTimeFormatter.ISO_OFFSET_DATE;
    public DateTimeFormatter longDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSz");

    public TimeZoneMap timeZoneMap = new TimeZoneMap();

    private Map<String, String> tzmap  = new HashMap<>();;

    public void buildDateTimeFromShortDateString(String sDate, String tz){

        timeZoneMap.buildEnumMap(tzmap);
        String tzOffSet = tzmap.get(tz);
        if (tzOffSet == null) tzOffSet = "+00:00";

        sDate = sDate.replaceAll("\\s", "") + "T00:00:00.000" + tzOffSet;
        zdtWithZoneOffset = ZonedDateTime.parse(sDate, longDateFormat);
        zdtInLocalTimeline = zdtWithZoneOffset.withZoneSameInstant(ZoneId.systemDefault());

        shortDate = zdtWithZoneOffset.format(shortDateFormat);
        longDateTime = zdtWithZoneOffset.format(longDateFormat);


    }

    public void buildDateTimeFromLongDateString(String lDate){

        zdtWithZoneOffset = ZonedDateTime.parse(lDate, longDateFormat);
        zdtInLocalTimeline = zdtWithZoneOffset.withZoneSameInstant(ZoneId.systemDefault());

    }
    public void buildDateTimeFromJavaDate(ZonedDateTime sDate){

        shortDate = sDate.format(shortDateFormat);
        longDateTime = sDate.format(longDateFormat);

    }

    public String convertSDateToString (Date jdate) {

        String year =  Integer.toString(jdate.getYear());
        String month = Integer.toString(jdate.getMonth());
        String day = Integer.toString(jdate.getDay());

        String standardDate = year + "-" + month + "-" + day;

        SimpleDateFormat ISOFormat  = new SimpleDateFormat("yyyy-MM-dd");
        Date newdate = new Date(standardDate);
        String formatedDate = ISOFormat.format(newdate);


        return formatedDate;
    }

}
