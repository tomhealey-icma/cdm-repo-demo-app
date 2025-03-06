package com.finxis.cdm.crossproductapp.util;

public class FinxisDateTimeCalendar {


    public String getSettlementDate(String unadjustedDate, String businessCenterCode, String adjustmentConvention){

        String adjustedDate = null;

        //select date from calendar database where date , businessCenterCode, adjustmentConvention
        // if date is null then return date else
        // add 1 day if not a new month then check else if new month subtract a day.

        adjustedDate = unadjustedDate;

        return adjustedDate;

    }
}
