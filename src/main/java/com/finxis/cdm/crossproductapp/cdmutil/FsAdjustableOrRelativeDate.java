package com.finxis.cdm.crossproductapp.cdmutil;

import cdm.base.datetime.*;
import cdm.base.datetime.metafields.FieldWithMetaBusinessCenterEnum;
import cdm.base.datetime.metafields.FieldWithMetaCommodityBusinessCalendarEnum;
import com.finxis.cdm.crossproductapp.util.FinxisDateTimeCalendar;
import com.opengamma.strata.basics.ReferenceData;
import com.opengamma.strata.basics.date.BusinessDayAdjustment;
import com.opengamma.strata.basics.date.BusinessDayConvention;
import com.opengamma.strata.basics.date.HolidayCalendarIds;
import com.rosetta.model.lib.meta.Reference;
import com.rosetta.model.lib.records.Date;
import com.rosetta.model.metafields.FieldWithMetaDate;
import cdm.base.datetime.AdjustableDate;
import cdm.base.datetime.AdjustableOrRelativeDate;
import com.finxis.cdm.crossproductapp.util.FinxisDateTime;
import com.rosetta.model.lib.records.Date;
import com.rosetta.model.metafields.MetaFields;
import com.rosetta.model.metafields.ReferenceWithMetaDate;

import java.util.List;

public class FsAdjustableOrRelativeDate  {


    public AdjustableOrRelativeDate createAdjustableDate(String unadjustedDate,
                                                         String businesDayConvention,
                                                         String businessCenter){

        AdjustableOrRelativeDate adjDate = new AdjustableOrRelativeDate.AdjustableOrRelativeDateBuilderImpl();
        FsCdmDateUtil fsCdmDateUtil = new FsCdmDateUtil();
        Date cdmUnadustedDate = fsCdmDateUtil.createCdmDateFromShortDateString(unadjustedDate);

        adjDate.toBuilder()
                .setAdjustableDate(AdjustableDate.builder()
                        .setUnadjustedDate(cdmUnadustedDate)
                        .setDateAdjustments(BusinessDayAdjustments.builder()
                                .setBusinessDayConvention(BusinessDayConventionEnum.valueOf(businesDayConvention))
                        .setBusinessCenters(BusinessCenters.builder()
                                .setBusinessCenter(List.of(FieldWithMetaBusinessCenterEnum.builder()
                                        .setValue(BusinessCenterEnum.valueOf(businessCenter))
                                                .setMeta(MetaFields.builder()
                                                        .setGlobalKey(businessCenter)))))))

                        .build();



        return adjDate;

    }

    public AdjustableOrRelativeDate createAdjustableDateCommodityCalendar(String unadjustedDate,
                                                         String businesDayConvention,
                                                         String commodityBusinessCalendar){

        AdjustableOrRelativeDate adjDate = new AdjustableOrRelativeDate.AdjustableOrRelativeDateBuilderImpl();
        FsCdmDateUtil fsCdmDateUtil = new FsCdmDateUtil();
        Date cdmUnadustedDate = fsCdmDateUtil.createCdmDateFromShortDateString(unadjustedDate);

        adjDate.toBuilder()
                .setAdjustableDate(AdjustableDate.builder()
                        .setUnadjustedDate(cdmUnadustedDate)
                        .setDateAdjustments(BusinessDayAdjustments.builder()
                                .setBusinessDayConvention(BusinessDayConventionEnum.valueOf(businesDayConvention))
                                .setBusinessCenters(BusinessCenters.builder()
                                        .setCommodityBusinessCalendar(List.of(FieldWithMetaCommodityBusinessCalendarEnum.builder()
                                                .setValue(CommodityBusinessCalendarEnum.valueOf(commodityBusinessCalendar))
                                                .setMeta(MetaFields.builder()
                                                        .setGlobalKey(commodityBusinessCalendar)))))))

                .build();



        return adjDate;

    }

    public AdjustedRelativeDateOffset createRelativeDate(String unadjustedDate,
                                                               String businesDayConvention,
                                                               String businessCenter,
                                                               String dayType,
                                                               String period,
                                                               String periodMultiplier,
                                                               String referenceDate){

        AdjustedRelativeDateOffset relDate = new AdjustedRelativeDateOffset.AdjustedRelativeDateOffsetBuilderImpl();

        FsCdmDateUtil fsCdmDateUtil = new FsCdmDateUtil();

        Date cdmUnadustedDate = fsCdmDateUtil.createCdmDateFromShortDateString(unadjustedDate);

        relDate.toBuilder()
                        .setDayType(DayTypeEnum.valueOf(dayType))
                        .setBusinessDayConvention(BusinessDayConventionEnum.valueOf(businesDayConvention))
                        .setBusinessCenters(BusinessCenters.builder()
                                .setBusinessCenter(List.of(FieldWithMetaBusinessCenterEnum.builder()
                                        .setValue(BusinessCenterEnum.valueOf(businessCenter))
                                        .setMeta(MetaFields.builder()
                                                .setGlobalKey(businessCenter)))))
                        .setDateRelativeTo(ReferenceWithMetaDate.builder()
                                .setReference(Reference.builder()
                                        .setReference(referenceDate)))
                .setPeriod(PeriodEnum.valueOf(period))
                .setPeriodMultiplier(Integer.parseInt(periodMultiplier))
                .setAdjustedDate(cdmUnadustedDate)
                .build();


        return relDate;

    }

    public AdjustableDate calculateAdjustedDate(AdjustableDate adjustableDate, Date unadjustedDate){

        FsCdmDateUtil fsCdmDateUtil = new FsCdmDateUtil();
        FinxisDateTimeCalendar finxisDateTimeCalendar = new FinxisDateTimeCalendar();

        String unadjustedDateStr = null;

        if(unadjustedDate == null)
            unadjustedDateStr = fsCdmDateUtil.getShortDateFromCdmDate(adjustableDate.getUnadjustedDate());
        else
            unadjustedDateStr = fsCdmDateUtil.getShortDateFromCdmDate(unadjustedDate);

        String businessCenterCode = adjustableDate.getDateAdjustments().getBusinessCenters().getBusinessCenter().get(0).getValue().toString();
        String adjustmentConvention = adjustableDate.getDateAdjustments().getBusinessDayConvention().toString();

        Date cdmUnadustedDate = fsCdmDateUtil.createCdmDateFromShortDateString(finxisDateTimeCalendar.getSettlementDate(unadjustedDateStr, businessCenterCode, adjustmentConvention));

        adjustableDate.toBuilder()
                .setAdjustedDateValue(cdmUnadustedDate )
                .build();

        return adjustableDate;
    }

    public AdjustableDate calculateRelativeDate(AdjustableDate adjustableDate, Date unadjustedDate){

        FsCdmDateUtil fsCdmDateUtil = new FsCdmDateUtil();
        FinxisDateTimeCalendar finxisDateTimeCalendar = new FinxisDateTimeCalendar();

        String unadjustedDateStr = null;

        if(unadjustedDate == null)
            unadjustedDateStr = fsCdmDateUtil.getShortDateFromCdmDate(adjustableDate.getUnadjustedDate());
        else
            unadjustedDateStr = fsCdmDateUtil.getShortDateFromCdmDate(unadjustedDate);

        String businessCenterCode = adjustableDate.getDateAdjustments().getBusinessCenters().getBusinessCenter().get(0).getValue().toString();
        String adjustmentConvention = adjustableDate.getDateAdjustments().getBusinessDayConvention().toString();

        Date cdmUnadustedDate = fsCdmDateUtil.createCdmDateFromShortDateString(finxisDateTimeCalendar.getSettlementDate(unadjustedDateStr, businessCenterCode, adjustmentConvention));

        adjustableDate.toBuilder()
                .setAdjustedDateValue(cdmUnadustedDate )
                .build();

        return adjustableDate;
    }


}
