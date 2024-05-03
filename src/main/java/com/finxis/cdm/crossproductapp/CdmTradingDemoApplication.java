package com.finxis.cdm.crossproductapp;


import cdm.base.math.NonNegativeQuantitySchedule;
import cdm.base.math.UnitType;
import cdm.base.math.metafields.FieldWithMetaNonNegativeQuantitySchedule;
import cdm.base.staticdata.asset.common.*;
import cdm.base.staticdata.asset.common.metafields.ReferenceWithMetaProductIdentifier;
import cdm.base.staticdata.party.*;
import cdm.event.common.*;
import cdm.event.common.functions.Create_BusinessEvent;
import cdm.event.common.functions.Create_TerminationInstruction;
import cdm.event.workflow.EventTimestamp;
import cdm.event.workflow.MessageInformation;
import cdm.event.workflow.Workflow;
import cdm.event.workflow.WorkflowStep;
import cdm.event.workflow.functions.Create_WorkflowStep;
import cdm.observable.asset.Price;
import cdm.observable.asset.PriceExpressionEnum;
import cdm.observable.asset.PriceTypeEnum;
import cdm.observable.asset.metafields.FieldWithMetaPriceSchedule;
import cdm.product.common.settlement.PriceQuantity;
import cdm.product.template.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finxis.cdm.crossproductapp.util.CdmUtil;
import com.finxis.cdm.crossproductapp.util.FileWriter;
import com.google.inject.Injector;
import com.regnosys.rosetta.common.hashing.GlobalKeyProcessStep;
import com.regnosys.rosetta.common.serialisation.RosettaObjectMapper;
import com.rosetta.model.lib.GlobalKey;
import com.rosetta.model.lib.RosettaModelObject;
import com.rosetta.model.lib.RosettaModelObjectBuilder;
import com.rosetta.model.lib.records.Date;
import com.rosetta.model.metafields.FieldWithMetaDate;
import cdm.base.staticdata.identifier.*;
import com.rosetta.model.metafields.FieldWithMetaString;
import com.rosetta.model.metafields.MetaFields;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.google.inject.Guice;

import cdm.base.staticdata.identifier.AssignedIdentifier;
import cdm.base.staticdata.party.Party;

import com.rosetta.model.lib.process.PostProcessStep;
import org.finos.cdm.CdmRuntimeModule;
import com.regnosys.rosetta.common.hashing.NonNullHashCollector;

import java.util.List;

public class CdmTradingDemoApplication {

    private final PostProcessStep keyProcessor;
    public CdmTradingDemoApplication () {keyProcessor = new GlobalKeyProcessStep(NonNullHashCollector::new);
    }


    private <T extends RosettaModelObject> T addGlobalKey(Class<T> type, T modelObject) {
        RosettaModelObjectBuilder builder = modelObject.toBuilder();
        keyProcessor.runProcessStep(type, builder);
        return type.cast(builder.build());
    }
    private String getGlobalReference(GlobalKey globalKey) {
        return globalKey.getMeta().getGlobalKey();
    }


    public static String getTradeState(String businessEventJson) throws JsonProcessingException {

        ObjectMapper rosettaObjectMapper = RosettaObjectMapper.getNewRosettaObjectMapper();
        BusinessEvent businessEventObj = new BusinessEvent.BusinessEventBuilderImpl();
        BusinessEvent businessEvent  = rosettaObjectMapper.readValue(businessEventJson, businessEventObj.getClass());


        TradeState tradeStateObj = new TradeState.TradeStateBuilderImpl();
        TradeState tradeState  = businessEvent.getAfter().get(0);

        String tradeStateJson = RosettaObjectMapper.getNewRosettaObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(tradeState);

        return tradeStateJson;

    }


}

