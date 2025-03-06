package com.finxis.cdm.crossproductapp.util;

import com.finxis.cdm.crossproductapp.util.FinxisDateTimeBase;
import com.finxis.cdm.crossproductapp.util.TimeZoneMap;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class FinxisDateTime {

    public DateTimeFormatter longDateFormat = DateTimeFormatter.ISO_ZONED_DATE_TIME;

    private FinxisDateTimeBase referenceDateTime = new FinxisDateTimeBase();

    private FinxisDateTimeBase UTCDateTime = new FinxisDateTimeBase();

    private FinxisDateTimeBase diffDateTime = new FinxisDateTimeBase();

    public TimeZoneMap timeZoneMap = new TimeZoneMap();

    private Map<String, String> tzmap  = new HashMap<>();;
    public FinxisDateTime createFinxisDateTime(Date date){

        FinxisDateTime finxisDateTime = new FinxisDateTime();

        return finxisDateTime;

    }

    public FinxisDateTime createFinxisDateTime(String date) {

        Pattern pattern = Pattern.compile("\\b([0123456789]{4}-[0123456789]{2}-[0123456789]{2})\\s?([A-Z]{3})?\\b");
        Matcher matcher = pattern.matcher(date);

        String tz = "UTC";
        String tztemp = null;

        FinxisDateTime finxisDateTime = new FinxisDateTime();
        finxisDateTime = null;

        if (matcher.matches() && matcher.groupCount()>1) {
            if (matcher.group(2) != null) {
                String tzmatch = matcher.group(2).trim();
                timeZoneMap.buildEnumMap(tzmap);
                tztemp = tzmap.get(tzmatch);
                if (tztemp == null) tz = "UTC";
                else tz = tzmatch;
            }

            String dateOnly = matcher.group(1).trim();
            referenceDateTime.buildDateTimeFromShortDateString(dateOnly,tz);

            ZonedDateTime zdtWithZoneOffset = ZonedDateTime.parse(referenceDateTime.longDateTime, longDateFormat);
            ZonedDateTime zdtInLocalTimeline = zdtWithZoneOffset.withZoneSameInstant(ZoneId.of("UTC"));
            UTCDateTime.buildDateTimeFromJavaDate(zdtInLocalTimeline);

        } else {

            pattern = Pattern.compile("\\b[\\d]{4}-[\\d]{2}-[\\d]{2}T[\\d]{2}:[\\d]{2}:[\\d]{2}.[\\d]{3}[zZ]?\\b");
            matcher = pattern.matcher(date);

            if (matcher.find()) {
                referenceDateTime.buildDateTimeFromLongDateString(date);
            }
        }

        return finxisDateTime;

    }

    public FinxisDateTimeBase getReferenceDateTime (){
        return referenceDateTime;
    }

    public FinxisDateTimeBase getUTCDateTime (){
        return UTCDateTime;
    }

    public FinxisDateTimeBase getDiffDateTimee (){
        return diffDateTime;
    }
}
