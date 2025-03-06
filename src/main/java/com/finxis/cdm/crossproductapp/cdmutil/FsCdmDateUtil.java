package com.finxis.cdm.crossproductapp.cdmutil;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import cdm.base.datetime.*;
import cdm.base.datetime.AdjustableDate;
import cdm.base.datetime.AdjustableDates;
import cdm.base.datetime.AdjustableOrRelativeDate;
import cdm.base.datetime.daycount.DayCountFractionEnum;
import cdm.base.datetime.daycount.metafields.FieldWithMetaDayCountFractionEnum;
import cdm.base.datetime.metafields.FieldWithMetaBusinessCenterEnum;
import cdm.base.datetime.metafields.ReferenceWithMetaBusinessCenters;
import cdm.base.math.NonNegativeQuantitySchedule;
import cdm.base.math.UnitType;
import cdm.base.math.metafields.FieldWithMetaNonNegativeQuantitySchedule;
import cdm.base.math.metafields.ReferenceWithMetaNonNegativeQuantitySchedule;
import cdm.base.staticdata.identifier.AssignedIdentifier;
import cdm.base.staticdata.identifier.Identifier;
import cdm.base.staticdata.identifier.TradeIdentifierTypeEnum;
import cdm.base.staticdata.party.*;
import cdm.base.staticdata.party.metafields.ReferenceWithMetaParty;
import cdm.base.staticdata.asset.common.metafields.FieldWithMetaProductIdentifier;
import cdm.base.staticdata.asset.common.*;
import cdm.event.common.ExecutionInstruction;
import cdm.event.common.Trade;
import cdm.event.common.TradeIdentifier;
import cdm.event.common.ExecutionDetails;
import cdm.event.common.*;
import cdm.observable.asset.Observable;
import cdm.product.asset.*;
import cdm.product.collateral.*;
import cdm.product.common.schedule.CalculationPeriodDates;
import cdm.product.common.schedule.PayRelativeToEnum;
import cdm.product.common.schedule.PaymentDates;
import cdm.product.common.schedule.RateSchedule;
import cdm.product.common.settlement.ResolvablePriceQuantity;
import cdm.observable.asset.*;
import cdm.observable.asset.metafields.FieldWithMetaPriceSchedule;
import cdm.observable.asset.FloatingRateOption;
import cdm.observable.asset.Price;
import cdm.observable.asset.PriceTypeEnum;
import cdm.observable.asset.metafields.ReferenceWithMetaPriceSchedule;
import cdm.product.common.settlement.*;
import cdm.product.template.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.regnosys.rosetta.common.hashing.GlobalKeyProcessStep;
import com.regnosys.rosetta.common.hashing.NonNullHashCollector;
import com.regnosys.rosetta.common.serialisation.RosettaObjectMapper;
import com.rosetta.model.lib.GlobalKey;
import com.rosetta.model.lib.RosettaModelObject;
import com.rosetta.model.lib.RosettaModelObjectBuilder;
import com.rosetta.model.lib.meta.Key;
import com.rosetta.model.lib.meta.Reference;
import com.rosetta.model.lib.process.PostProcessStep;
import com.rosetta.model.lib.records.Date;
import com.rosetta.model.lib.records.DateImpl;
import com.rosetta.model.metafields.FieldWithMetaDate;
import com.rosetta.model.metafields.FieldWithMetaString;
import com.rosetta.model.metafields.MetaFields;
import com.rosetta.model.lib.meta.Reference;
import com.rosetta.model.lib.records.Date;
import org.joda.time.DateTime;


import java.io.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.rosetta.model.lib.records.Date.of;


import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FsCdmDateUtil {

    private final PostProcessStep keyProcessor;
    public FsCdmDateUtil () {
        keyProcessor = new GlobalKeyProcessStep(NonNullHashCollector::new);
    }

    /**
     * Utility to post process a {@link RosettaModelObject} to add ll gloval keys.
     */
    private <T extends RosettaModelObject> T addGlobalKey(Class<T> type, T modelObject) {
        RosettaModelObjectBuilder builder = modelObject.toBuilder();
        keyProcessor.runProcessStep(type, builder);
        return type.cast(builder.build());
    }

    /**
     * Utility to get the global reference string from a {@link GlobalKey} instance.
     */
    private String getGlobalReference(GlobalKey globalKey) {
        return globalKey.getMeta().getGlobalKey();
    }
    public FieldWithMetaDate createTradeDate(int y, int m, int d) {

        Date dt;
        dt = of(y, m, d);

        return FieldWithMetaDate.builder().setValue(dt).build();

    }

    public FieldWithMetaDate createCdmMetaDateFromLongDateString(String dateStr){


        DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSz");
        ZonedDateTime zdtWithZoneOffset = ZonedDateTime.parse(dateStr, formatter);

        FieldWithMetaDate cdmDate = addGlobalKey(FieldWithMetaDate.class,
                createTradeDate(zdtWithZoneOffset.getYear(), zdtWithZoneOffset.getMonthValue(), zdtWithZoneOffset.getDayOfMonth()));

        return cdmDate;

    }

    public FieldWithMetaDate createCdmMetaDateFromShortDateString(String dateStr){


        String tradeDateStr = dateStr.replaceAll("\\s", "") + "T00:00:00.000+00:00";
        DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSz");
        ZonedDateTime zdtWithZoneOffset = ZonedDateTime.parse(tradeDateStr, formatter);

        FieldWithMetaDate cdmDate = addGlobalKey(FieldWithMetaDate.class,
                createTradeDate(zdtWithZoneOffset.getYear(), zdtWithZoneOffset.getMonthValue(), zdtWithZoneOffset.getDayOfMonth()));

        return cdmDate;

    }

    public Date createCdmDateFromShortDateString(String dateStr){

        DateTimeFormatter ISOFormat  = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String year = dateStr.substring(0,4);
        String month = dateStr.substring(5,7);
        String day = dateStr.substring(8,10);

        String standardDate = year + "-" + month + "-" + day;

        Date cdmDate= Date.of(Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(day) );
        return cdmDate;

    }
    public FieldWithMetaDate createCdmMetaDateFromFixShortDateString(String dateStr){


        DateTimeFormatter fixFormat  = DateTimeFormatter.ofPattern("yyyyMMdd");

        String year = dateStr.substring(0,4);
        String month = dateStr.substring(4,6);
        String day = dateStr.substring(6,8);

        String standardDate = year + "-" + month + "-" + day;

        return  createCdmMetaDateFromShortDateString(standardDate);

    }

    public String createStandardDateFromFixShortDateString(String dateStr){


        DateTimeFormatter fixFormat  = DateTimeFormatter.ofPattern("yyyyMMdd");

        String year = dateStr.substring(0,4);
        String month = dateStr.substring(4,6);
        String day = dateStr.substring(6,8);

        String standardDate = year + "-" + month + "-" + day;

        return standardDate;

    }


    public String getShortDateFromCdmDate(Date date){

        Integer year =  date.getYear();
        String month = Integer.toString(date.getMonth());
        String day = Integer.toString(date.getDay());

        LocalDate ldate = LocalDate.of(date.getYear(), date.getMonth(),date.getDay());

        DateTimeFormatter shortDateFormat =
                DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String formatedDate = ldate.format(shortDateFormat);

        return formatedDate;


    }










}
