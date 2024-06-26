/*******************************************************************************
 * Copyright (c) icmagroup.org  All rights reserved.
 *
 * This file is part of the International Capital Market Association (ICMA)
 * CDM for Repo and Bonds Demo
 *
 * This file is intended for demo purposes only and may not be distributed
 * or used in any commercial capacity other then its intended purpose.
 *
 * This file is provided AS IS with NO WARRANTY OF ANY KIND, INCLUDING
 * THE WARRANTY OF DESIGN, MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE.
 *
 * Prepared by FINXIS LLC, New York, NY.
 *
 * Contact International Capital Market Association (ICMA),110 Cannon Street,
 * London EC4N 6EU, ph. +44 20 7213 0310, if you have any questions.
 *
 ******************************************************************************/
package com.finxis.cdm.crossproductapp;

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
import cdm.legaldocumentation.common.*;
import cdm.legaldocumentation.master.MasterAgreementTypeEnum;
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
import com.rosetta.model.metafields.FieldWithMetaDate;
import com.rosetta.model.metafields.FieldWithMetaString;
import com.rosetta.model.metafields.MetaFields;
import com.rosetta.model.lib.meta.Reference;
import com.rosetta.model.lib.records.Date;


import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.rosetta.model.lib.records.Date.of;



public class RepoLegalAgreementCreation {

	private final PostProcessStep keyProcessor;

	public RepoLegalAgreementCreation() {
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


	public LegalAgreement createLegalAgreementObject(
			String agreementNameStr,
			String agreementGoverningLawStr,
			String agreementVintageStr,
			String agreementPublisherStr,
			String agreementDateStr,
			String agreementIdentifierStr,
			String agreementEffectiveDate,
			//List<String> agreementContractualParty,
			//List<String> otherParty,
			String agreementUrl
	) throws JsonProcessingException {

		Integer vintage;

		try{
			vintage = Integer.parseInt(agreementVintageStr);
		} catch (NumberFormatException e) {
			vintage = 0;
		}

		LegalAgreement lga;

		if (agreementNameStr.equals("GMRA")) {
			lga = LegalAgreement.builder()
					.setAgreementDate(Date.of(2011,1,24))
					.setLegalAgreementIdentification(LegalAgreementIdentification.builder()
							.setVintage(vintage)
							.setPublisher(LegalAgreementPublisherEnum.ICMA)
							.setGoverningLaw(GoverningLawEnum.GBEN)
							.setAgreementName(AgreementName.builder()
									.setMasterAgreementTypeValue(MasterAgreementTypeEnum.GMRA)))

					.build();
		}else{
			lga = LegalAgreement.builder()
					.setLegalAgreementIdentification(LegalAgreementIdentification.builder()
					.build());

		}

		return lga;

	}
}