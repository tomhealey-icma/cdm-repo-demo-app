/*******************************************************************************
 * Copyright (c) icmagroup.org  All rights reserved.
 *
 * This file is part of the International Capital Market Association (ICMA)
 * CDM for Repo and Bonds Demo
 *
 * This file is intended for demo purposes only and may not be distributed
 * or used in any commercial capacity other than its intended purpose.
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

import cdm.base.datetime.*;
import cdm.base.datetime.daycount.DayCountFractionEnum;
import cdm.base.datetime.daycount.metafields.FieldWithMetaDayCountFractionEnum;
import cdm.base.datetime.metafields.FieldWithMetaBusinessCenterEnum;
import cdm.base.datetime.metafields.ReferenceWithMetaBusinessCenters;
import cdm.base.math.ArithmeticOperationEnum;
import cdm.base.math.NonNegativeQuantitySchedule;
import cdm.base.math.Quantity;
import cdm.base.math.UnitType;
import cdm.base.math.metafields.FieldWithMetaNonNegativeQuantitySchedule;
import cdm.base.math.metafields.ReferenceWithMetaNonNegativeQuantitySchedule;
import cdm.base.staticdata.asset.common.*;
import cdm.base.staticdata.asset.common.metafields.FieldWithMetaAssetClassEnum;
import cdm.base.staticdata.asset.common.metafields.ReferenceWithMetaProductIdentifier;
import cdm.base.staticdata.asset.rates.FloatingRateIndexEnum;
import cdm.base.staticdata.party.*;
import cdm.event.common.*;
import cdm.event.common.metafields.ReferenceWithMetaCollateralPortfolio;
import cdm.observable.asset.*;
import cdm.observable.asset.metafields.FieldWithMetaPriceSchedule;
import cdm.observable.asset.metafields.ReferenceWithMetaPriceSchedule;
import cdm.product.asset.FixedRateSpecification;
import cdm.product.asset.FloatingRateSpecification;
import cdm.product.asset.InterestRatePayout;
import cdm.product.asset.RateSpecification;
import cdm.product.collateral.*;
import cdm.product.common.schedule.CalculationPeriodDates;
import cdm.product.common.schedule.PayRelativeToEnum;
import cdm.product.common.schedule.PaymentDates;
import cdm.product.common.schedule.RateSchedule;
import cdm.product.common.settlement.*;
import cdm.product.template.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.finxis.cdm.crossproductapp.util.CdmUtil;
import com.finxis.cdm.crossproductapp.util.IcmaRepoUtil;
import com.finxis.cdm.crossproductapp.util.FinxisDateTime;
import com.regnosys.rosetta.common.hashing.GlobalKeyProcessStep;
import com.regnosys.rosetta.common.hashing.NonNullHashCollector;
import com.rosetta.model.lib.GlobalKey;
import com.rosetta.model.lib.RosettaModelObject;
import com.rosetta.model.lib.RosettaModelObjectBuilder;
import com.rosetta.model.lib.meta.Reference;
import com.rosetta.model.lib.process.PostProcessStep;
import com.rosetta.model.lib.records.Date;
import com.rosetta.model.metafields.*;
import com.rosetta.model.metafields.FieldWithMetaDate;
import com.rosetta.model.metafields.FieldWithMetaString;
import com.rosetta.model.metafields.MetaFields;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.rosetta.model.lib.records.Date.of;

//This class is used to generate a repo execution using an execution instruction and business event.
/*func Create_ExecutionInstruction:
    inputs:
        product Product (1..1) <"Defines the financial product to be executed and contract formed.">
        priceQuantity PriceQuantity (1..*) <"Specifies the price, quantity, and optionally the observable and settlement terms for use in a trade or other purposes.">
        counterparty Counterparty (2..2) <"Maps two defined parties to counterparty enums for the transacted product.">
        ancillaryParty AncillaryParty (0..*) <"Maps any ancillary parties, e.g. parties involved in the transaction that are not one of the two principal parties.">
        parties Party (2..*) <"Defines all parties to that execution, including agents and brokers.">
        partyRoles PartyRole (0..*) <"Defines the role(s) that party(ies) may have in relation to the execution.">
        executionDetails ExecutionDetails (0..1) <"Specifies the type of execution, e.g. via voice or electronically.">
        tradeDate date (1..1) <"Denotes the trade/execution date.">
        tradeIdentifier TradeIdentifier (1..*) <"Denotes one or more identifiers associated with the transaction.">
    output:
        instruction ExecutionInstruction (1..1)
*/

public class RepoExecutionCreation{
	
	private final PostProcessStep keyProcessor;

    public RepoExecutionCreation() {
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


	public ExecutionInstruction createRepoExecutionInstruction (
				String tradeDateStr,
				String purchaseDateStr,
				String repurchaseDateStr,
				String firmTradeIdStr,
				String tradeUTIStr,
				String buyerLEIStr,
				String buyerNameStr,
				String sellerLEIStr,
				String sellerNameStr,
				String collateralDescriptionStr,
				String collateralISINStr,
				String collateralQuantityStr,
				String collateralCleanPriceStr,
				String collateralDirtyPriceStr,
				String collateralAdjustedValueStr,
				String collateralCurrencyStr,
				String repoRateStr,
				String cashCurrencyStr,
				String cashQuantityStr,
				String haircutStr,
				String termTypeStr,
				String terminationOptionStr,
				String noticePeriodStr,
				String deliveryMethodStr,
				String substitutionAllowedStr,
				String rateTypeStr,
				String dayCountFractionStr,
				String purchasePriceStr,
				String repurchasePriceStr,
				String agreementNameStr,
				String agreementGoverningLawStr,
				String agreementVintageStr,
				String agreementPublisherStr,
				String agreementDateStr,
				String agreementIdentifierStr,
				String agreementEffectiveDate,
				String agreementUrl,
				String businessCenter,
				String execVenueCode,
				String execVenueScheme,
				String settlementAgentLEIStr,
				String settlementAgentNameStr,
				String ccpLeiStr,
				String ccpNameStr,
				String csdParticipantLeiStr,
				String csdParticipantNameStr,
				String brokerLeiStr,
				String brokerNameStr,
				String tripartyLeiStr,
				String tripartyNameStr,
				String clearingMemberLeiStr,
				String clearingMemberNameStr,
				String floatingRateReferenceStr,
				String floatingRateReferencePeriodStr,
				String floatingRateReferenceMultiplierStr,
				String floatingRateResetFreqStr,
				String floatingRateResetMultiplierStr,
				String floatingRatePaymentFreqStr,
				String floatingRatePaymentMultiplierStr,
				String transactionType
	) throws JsonProcessingException {
		
		RepoExecutionCreation rc = new RepoExecutionCreation();

		DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSz");

		String dateTemp = tradeDateStr;
		FinxisDateTime finxisDateTime = new FinxisDateTime();
		finxisDateTime.createFinxisDateTime(dateTemp);
		dateTemp  = finxisDateTime.getReferenceDateTime().longDateTime;


		//dateTemp = dateTemp.replaceAll("\\s", "") + "T00:00:00.000+00:00";
		ZonedDateTime zdtWithZoneOffset = ZonedDateTime.parse(dateTemp, formatter);
		ZonedDateTime zdtInLocalTimeline = zdtWithZoneOffset.withZoneSameInstant(ZoneId.systemDefault());

		IcmaRepoUtil ru = new IcmaRepoUtil();
		CdmUtil cdmUtil = new CdmUtil();

		FieldWithMetaDate tradeDate = cdmUtil.createCdmDateFromShortDateString(tradeDateStr);

		TradeIdentifier tradeIdentifier = ru.createRepoTradeIdentifier(tradeUTIStr, "UnqTradIdr", "5493000SCC07UI6DB380");
		TradeIdentifier firmTradeIdentifer = ru.createRepoTradeIdentifier(firmTradeIdStr, "UnqTradIdr", sellerLEIStr);

		List<TradeIdentifier> tradeIdentifierList = List.of(tradeIdentifier , firmTradeIdentifer);

		List<Party> parties = new ArrayList<Party>();
		//Party 1 is defined in the interest rate payout model as the payer and thus represents the repo seller also referred to as the collateral giver.
        Party party1 = ru.createRepoParty(sellerLEIStr,"LEI",sellerNameStr);
		parties.add(party1);
				
		//Party 2 is defined in the interest rate payout model as the receiver and thus represents the repo buyer also referred to as the collateral taker.
        Party party2 = ru.createRepoParty(buyerLEIStr,"LEI",buyerNameStr);
		parties.add(party2);

		Party settlementAgent = ru.createRepoParty(settlementAgentLEIStr,"LEI",settlementAgentNameStr);
		parties.add(settlementAgent);

		Party ccp = ru.createRepoParty(ccpLeiStr,"LEI",ccpNameStr);
		parties.add(ccp);

		Party csdparticipant = ru.createRepoParty(csdParticipantLeiStr,"LEI",csdParticipantNameStr);
		parties.add(csdparticipant);

		Party broker = ru.createRepoParty(brokerLeiStr,"LEI",brokerNameStr);
		parties.add(broker);

		Party triparty = ru.createRepoParty(tripartyLeiStr,"LEI",tripartyNameStr);
		parties.add(triparty);

		Party clearingMember = ru.createRepoParty(clearingMemberLeiStr,"LEI",clearingMemberNameStr);
		parties.add(clearingMember);

		while (parties.remove(null));

		//List<Party> parties = List.of(party1, party2, settlementAgent,ccp, csdparticipant,broker,triparty,clearingMember);

		List<PartyRole> partyRoles = new ArrayList<PartyRole>();

		PartyRole party1Role = ru.createRepoPartyRole(party1, buyerLEIStr, "SELLER");
		partyRoles.add(party1Role);

		PartyRole party2Role = ru.createRepoPartyRole(party2, sellerLEIStr,"BUYER");
		partyRoles.add(party2Role);

		PartyRole settlementAgentRole = ru.createRepoPartyRole(settlementAgent, settlementAgentLEIStr, "SETTLEMENT_AGENT");
		partyRoles.add(settlementAgentRole);

		PartyRole ccpRole = ru.createRepoPartyRole(ccp, ccpLeiStr, "CLEARING_ORGANIZATION");
		partyRoles.add(ccpRole);

		PartyRole tripartyRole = ru.createRepoPartyRole(triparty, tripartyLeiStr, "CLEARING_ORGANIZATION");
		partyRoles.add(tripartyRole);

		PartyRole  brokerRole = ru.createRepoPartyRole(broker, brokerLeiStr, "EXECUTING_BROKER");
		partyRoles.add(brokerRole);

		PartyRole  clearingMemberRole = ru.createRepoPartyRole(clearingMember, clearingMemberLeiStr, "CLEARING_CLIENT");
		partyRoles.add(clearingMemberRole);

		PartyRole  csdParticipantRole = ru.createRepoPartyRole(csdparticipant, csdParticipantLeiStr, "CLEARING_CLIENT");
		partyRoles.add(csdParticipantRole);

		while (partyRoles.remove(null));

		//List<PartyRole> partyRoles = List.of(partyRole1, partyRole2, settlementAgentRole,ccpRole,csdParticipantRole,brokerRole,tripartyRole,clearingMemberRole);
		
		Counterparty counterparty1 = ru.createRepoCounterparty(party1, "PARTY_1");
		Counterparty counterparty2 = ru.createRepoCounterparty(party2, "PARTY_2");
		List<Counterparty> counterparties = List.of(counterparty1, counterparty2);
		
		double cashQuantity = Double.parseDouble(purchasePriceStr);
		double repurchaseQuantity = Double.parseDouble(repurchasePriceStr);
		double repoInterest = repurchaseQuantity-cashQuantity;
		double collateralQuantity = Double.parseDouble(collateralQuantityStr);
		double repoRate = Double.parseDouble(repoRateStr);
		double haircut = Double.parseDouble(haircutStr);
		double collateralCleanPrice = Double.parseDouble(collateralCleanPriceStr);
		double collateralDirtyPrice = Double.parseDouble(collateralDirtyPriceStr);
			
		PriceQuantity repoPriceQuantity = addGlobalKey(PriceQuantity.class,
				createRepoPriceQuantity(cashCurrencyStr, cashQuantity, repoInterest, repurchaseQuantity, repoRate));
		
		PriceQuantity collateralPriceQuantity = addGlobalKey(PriceQuantity.class,
				createCollateralPriceQuantity(cashCurrencyStr, cashQuantity, collateralCurrencyStr, collateralQuantity, collateralCleanPrice, collateralDirtyPrice, repoRate,collateralISINStr,""));
		
		List<PriceQuantity> repoPriceQuantityList = List.of(repoPriceQuantity, collateralPriceQuantity);

		//purchaseDateStr = purchaseDateStr.replaceAll("\\s", "") + "T00:00:00.000+00:00";

		finxisDateTime.createFinxisDateTime(purchaseDateStr);
		purchaseDateStr = finxisDateTime.getReferenceDateTime().longDateTime;

		zdtWithZoneOffset = ZonedDateTime.parse(purchaseDateStr, formatter);
		zdtInLocalTimeline = zdtWithZoneOffset.withZoneSameInstant(ZoneId.systemDefault());

		Date effectiveDate = of(zdtWithZoneOffset.getYear(), zdtWithZoneOffset.getMonthValue(), zdtWithZoneOffset.getDayOfMonth());

		//System.out.println("Purchase Date:" + zdtWithZoneOffset);

		Date terminationDate = null; //An open repo has a null termination date

		if(termTypeStr.equals("FIXED") || termTypeStr.equals("TERM")) {

			//repurchaseDateStr = repurchaseDateStr.replaceAll("\\s", "") + "T00:00:00.000+00:00";

			finxisDateTime.createFinxisDateTime(repurchaseDateStr );
			repurchaseDateStr  = finxisDateTime.getReferenceDateTime().longDateTime;
			zdtWithZoneOffset = ZonedDateTime.parse(repurchaseDateStr, formatter);
			zdtInLocalTimeline = zdtWithZoneOffset.withZoneSameInstant(ZoneId.systemDefault());
			terminationDate = of(zdtWithZoneOffset.getYear(), zdtWithZoneOffset.getMonthValue(), zdtWithZoneOffset.getDayOfMonth());
		}

		Product repoProduct = createRepoProduct(effectiveDate, terminationDate, repoRate, haircut , cashCurrencyStr,
				cashQuantity, repurchaseQuantity, collateralCurrencyStr, collateralQuantity, collateralCleanPrice, collateralDirtyPrice,
				repoRate, collateralISINStr, "", businessCenter, deliveryMethodStr, purchaseDateStr, repurchaseDateStr,settlementAgentNameStr,rateTypeStr, termTypeStr,
				floatingRateReferenceStr, floatingRateReferencePeriodStr, floatingRateReferenceMultiplierStr, floatingRateResetFreqStr, floatingRateResetMultiplierStr,
				floatingRatePaymentFreqStr, floatingRatePaymentMultiplierStr, transactionType);

		String execType;

		if (execVenueCode != null ) {
			if (execVenueCode.equals("OTC"))
				execType = "OFF_FACILITY";
			else
				execType = "ON_VENUE";
		} else
			execType = "OFF_FACILITY";

		String execVenueName = ""; //Possibility to add lookup based on code;
		ExecutionDetails executionDetails = createRepoExecutionDetails(execType,execVenueCode, execVenueScheme,execVenueName);

		return ExecutionInstruction.builder()
			.setProduct(repoProduct)
			.addPriceQuantity(repoPriceQuantityList)
			.addCounterparty(counterparties)
			.addParties(parties)
			.addPartyRoles(partyRoles)
			.setExecutionDetails(executionDetails)
			.setTradeDate(tradeDate)
			.addTradeIdentifier(tradeIdentifierList)
			.build();
	}
	
	private Product createRepoProduct(
			Date effectiveDate,
			Date terminationDate,
			double repoRate,
			double haircut,
			String cashCurrency,
			double cashQuantity,
			double repurchaseQuantity,
			String collateralCurrency,
			double collateralQuantity,
			double collteralCleanPrice,
			double collateralDirtyPrice,
			double rate,
			String collateralISINStr,
			String scheme,
			String businessCenter,
			String deliveryMethodStr,
			String purchaseDateStr,
			String repurchaseDateStr,
			String settlementAgentNameStr,
			String rateType,
			String termType,
			String floatingRateReferenceStr,
			String floatingRateReferencePeriodStr,
			String floatingRateReferenceMultiplierStr,
			String floatingRateResetFreqStr,
			String floatingRateResetMultiplierStr,
			String floatingRatePaymentFreqStr,
			String floatingRatePaymentMultiplierStr,
			String transactionType
			){
		
		BigDecimal repoRateBD = new BigDecimal(repoRate);
		BigDecimal haircutBD = new BigDecimal(haircut);
		
		ContractualProduct contractualRepoProduct = createContractualRepoProduct(
							effectiveDate, terminationDate, repoRateBD, haircutBD, cashCurrency,
							cashQuantity, repurchaseQuantity,collateralCurrency, collateralQuantity, collteralCleanPrice, collateralDirtyPrice,
							rate, collateralISINStr, scheme,businessCenter,deliveryMethodStr,purchaseDateStr,repurchaseDateStr,settlementAgentNameStr,rateType, termType,
				floatingRateReferenceStr, floatingRateReferencePeriodStr, floatingRateReferenceMultiplierStr, floatingRateResetFreqStr, floatingRateResetMultiplierStr,
				floatingRatePaymentFreqStr, floatingRatePaymentMultiplierStr, transactionType);
		
		return Product.builder()
					.setContractualProduct(contractualRepoProduct)
					.build();
		
	}
	
	
	private ContractualProduct createContractualRepoProduct(
			Date effectiveDate,
			Date terminationDate,
			BigDecimal repoRateBD,
			BigDecimal haircutBD,
			String cashCurrency,
			double cashQuantity,
			double repurchaseQuantity,
			String collateralCurrency,
			double collateralQuantity,
			double collteralCleanPrice,
			double collateralDirtyPrice,
			double rate,
			String collateralISINStr,
			String scheme,
			String businessCenter,
			String deliveryMethodStr,
			String purchaseDateStr,
			String repurchaseDateStr,
			String settlementAgentNameStr,
			String rateType,
			String termType,
			String floatingRateReferenceStr,
			String floatingRateReferencePeriodStr,
			String floatingRateReferenceMultiplierStr,
			String floatingRateResetFreqStr,
			String floatingRateResetMultiplierStr,
			String floatingRatePaymentFreqStr,
			String floatingRatePaymentMultiplierStr,
			String transactionType
	) {
        return ContractualProduct.builder()
				.addProductTaxonomy(ProductTaxonomy.builder()
					.setSource(TaxonomySourceEnum.valueOf("CFI"))
					.setValue(TaxonomyValue.builder()
							.setNameValue("LRSTXD"))
						.setPrimaryAssetClass(FieldWithMetaAssetClassEnum.builder()
								.setValue(AssetClassEnum.MONEY_MARKET)))
                .setEconomicTerms(createEconomicTerms(effectiveDate, terminationDate, repoRateBD, haircutBD,
						cashCurrency, cashQuantity, repurchaseQuantity, collateralCurrency, collateralQuantity,
						collteralCleanPrice, collateralDirtyPrice, rate, collateralISINStr, scheme,businessCenter,deliveryMethodStr,
						purchaseDateStr, repurchaseDateStr,settlementAgentNameStr, rateType, termType,
						floatingRateReferenceStr, floatingRateReferencePeriodStr, floatingRateReferenceMultiplierStr, floatingRateResetFreqStr, floatingRateResetMultiplierStr, floatingRatePaymentFreqStr, floatingRatePaymentMultiplierStr))
				.build();

    }

	private EconomicTerms createEconomicTerms(
			Date effectiveDate,
			Date terminationDate,
			BigDecimal repoRateBD,
			BigDecimal haircutBD,
			String cashCurrency,
			double cashQuantity,
			double repurchaseQuantity,
			String collateralCurrency,
			double collateralQuantity,
			double collteralCleanPrice,
			double collateralDirtyPrice,
			double rate,
			String collateralISINStr,
			String scheme,
			String businessCenter,
			String deliveryMethodStr,
			String purchaseDateStr,
			String repurchaseDateStr,
			String settlementAgentNameStr,
			String rateType,
			String termType,
			String floatingRateReferenceStr,
			String floatingRateReferencePeriodStr,
			String floatingRateReferenceMultiplierStr,
			String floatingRateResetFreqStr,
			String floatingRateResetMultiplierStr,
			String floatingRatePaymentFreqStr,
			String floatingRatePaymentMultiplierStr
			){

		EconomicTerms ecterms = null;

		BusinessCenterEnum businessCenterEnum = null;

		if (businessCenter == null )
			businessCenterEnum = BusinessCenterEnum.GBLO;
		else
			businessCenterEnum = BusinessCenterEnum.valueOf(businessCenter);


		ProductIdentifier collateralId = ProductIdentifier.builder()
				.setIdentifier(FieldWithMetaString.builder()
						.setValue(collateralISINStr)
						.build());

		GlobalKey collateralIdKey = addGlobalKey(ProductIdentifier.class, collateralId);

		List<? extends FieldWithMetaBusinessCenterEnum> businessCenterEnumList =
				List.of(FieldWithMetaBusinessCenterEnum.builder()
								.setValue(businessCenterEnum)
						.setMeta(MetaFields.builder()
								.build())
						.build());

		AdjustableOrRelativeDate effectiveAdjustableDate = AdjustableOrRelativeDate.builder()
				.setAdjustableDate(AdjustableDate.builder()
						.setUnadjustedDate(effectiveDate)
						.setDateAdjustments(BusinessDayAdjustments.builder()
								//Repo purchase dates are not subject to adjustment unless agree to by counterparties
								.setBusinessDayConvention(BusinessDayConventionEnum.NONE)
								.setBusinessCenters(BusinessCenters.builder()
										.setBusinessCenter(businessCenterEnumList))))
				.setMeta(MetaFields.builder()
						.setScheme(scheme)
						.setExternalKey("PurchaseDate")
						.setGlobalKey("PurchaseDate"))
				.build();

		if(rateType.equals("FIXED")) {

			ecterms = EconomicTerms.builder()
					.setEffectiveDate(effectiveAdjustableDate)
					.setPayout(Payout.builder()
							.addInterestRatePayout(createFixedRateRepoPayout(effectiveDate, terminationDate, repoRateBD, cashCurrency,
									cashQuantity, repurchaseQuantity, collateralCurrency, collateralQuantity, collteralCleanPrice, collateralDirtyPrice,
									rate, collateralISINStr, scheme, deliveryMethodStr, purchaseDateStr, repurchaseDateStr, settlementAgentNameStr))
							.addAssetPayout(createRepoCollateralPayout(deliveryMethodStr, effectiveAdjustableDate, collateralISINStr)))
					.setTerminationDate(AdjustableOrRelativeDate.builder()
							.setAdjustableDate(AdjustableDate.builder()
									.setUnadjustedDate(terminationDate)
									.setDateAdjustments(BusinessDayAdjustments.builder()
											.setBusinessDayConvention(BusinessDayConventionEnum.NONE)
											.setMeta(MetaFields.builder()
													.setScheme(scheme)
													.setExternalKey("RepurchaseDate")
													.setGlobalKey("RepurchaseDate")))))
					.setCollateral(createCollateral(collateralISINStr, haircutBD))
					.build();

		} else if (rateType.equals("FLOAT")){

			ecterms = EconomicTerms.builder()
					.setEffectiveDate(effectiveAdjustableDate)
					.setPayout(Payout.builder()
							.addInterestRatePayout(createFloatingRateRepoPayout(effectiveDate, terminationDate, repoRateBD, cashCurrency,
									cashQuantity, repurchaseQuantity, collateralCurrency, collateralQuantity, collteralCleanPrice, collateralDirtyPrice,
									rate, collateralISINStr, scheme, deliveryMethodStr, purchaseDateStr, repurchaseDateStr, settlementAgentNameStr,
									floatingRateReferenceStr, floatingRateReferencePeriodStr, floatingRateReferenceMultiplierStr, floatingRateResetFreqStr, floatingRateResetMultiplierStr, floatingRatePaymentFreqStr, floatingRatePaymentMultiplierStr))
							.addAssetPayout(createRepoCollateralPayout(deliveryMethodStr, effectiveAdjustableDate, collateralISINStr)))
					.setTerminationDate(AdjustableOrRelativeDate.builder()
							.setAdjustableDate(AdjustableDate.builder()
									.setUnadjustedDate(terminationDate)
									.setDateAdjustments(BusinessDayAdjustments.builder()
											.setBusinessDayConvention(BusinessDayConventionEnum.NONE)
											.setMeta(MetaFields.builder()
													.setScheme(scheme)
													.setExternalKey("RepurchaseDate")
													.setGlobalKey("RepurchaseDate")))))
					.setCollateral(createCollateral(collateralISINStr, haircutBD))
					.build();

		}
			return ecterms;
	}


	private Collateral createCollateral(String collateralISINStr, BigDecimal haircutBD){

		ProductIdentifier collateralId = ProductIdentifier.builder()
				.setIdentifier(FieldWithMetaString.builder()
						.setValue(collateralISINStr)
						.build());

		GlobalKey collateralIdKey = addGlobalKey(ProductIdentifier.class, collateralId);

		Collateral collateral = Collateral.builder()
					.setCollateralPortfolio(List.of(ReferenceWithMetaCollateralPortfolio.builder()
							.setValue(CollateralPortfolio.builder()
									.addCollateralPosition(List.of(CollateralPosition.builder()

									.setTreatment(CollateralTreatment.builder()
											.setValuationTreatment(CollateralValuationTreatment.builder()
													.setHaircutPercentage(haircutBD)))
													.setProduct(Product.builder()
														.setSecurity(Security.builder()
																.setProductIdentifier(List.of(ReferenceWithMetaProductIdentifier.builder()
																		.setValue(ProductIdentifier.builder()
																				.setSource(ProductIdTypeEnum.ISIN)
																				.setIdentifier(FieldWithMetaString.builder()
																						.setValue(collateralISINStr)
																						.setMeta(MetaFields.builder()
																								.setGlobalKey(getGlobalReference(collateralIdKey)))
																				)))))))))))

				.build();

		return collateral;

	}
	private List<EligibleCollateralCriteria> createRepoHairCut(BigDecimal haircut) {

		return List.of(EligibleCollateralCriteria.builder()
							.setTreatment(CollateralTreatment.builder()
								.setValuationTreatment(CollateralValuationTreatment.builder()
									.setHaircutPercentage(haircut)))
				.build());

	}
	
	private AssetPayout createRepoCollateralPayout(
			String deliveryMethod,
			AdjustableOrRelativeDate effectiveDate,
			String collateralISINStr
	){

		ProductIdentifier collateralId = ProductIdentifier.builder()
										.setIdentifier(FieldWithMetaString.builder()
										.setValue(collateralISINStr)
										.build());

		GlobalKey collateralIdKey = addGlobalKey(ProductIdentifier.class, collateralId);

		return AssetPayout.builder()
                .setPayerReceiver(PayerReceiver.builder()
                        .setPayer(CounterpartyRoleEnum.PARTY_1)
                        .setReceiver(CounterpartyRoleEnum.PARTY_2))
				.setAssetLeg(createAssetPayoutLeg(deliveryMethod, effectiveDate))
				.setSecurityInformation(Product.builder()
						.setSecurity(Security.builder()
								.setProductIdentifier(List.of(ReferenceWithMetaProductIdentifier.builder()
										.setValue(ProductIdentifier.builder()
												.setSource(ProductIdTypeEnum.ISIN)
												.setIdentifier(FieldWithMetaString.builder()
														.setValue(collateralISINStr)
														.setMeta(MetaFields.builder()
																.setGlobalKey(getGlobalReference(collateralIdKey)))
																.build()))
								)
						))

				.build());
		
	}

	private List<AssetLeg> createAssetPayoutLeg (
			String deliveryMethod,
			AdjustableOrRelativeDate effectiveDate){

		return List.of(AssetLeg.builder()
				.setDeliveryMethod(DeliveryMethodEnum.DELIVERY_VERSUS_PAYMENT)
				.setSettlementDate(AdjustableOrRelativeDate.builder()
						.setRelativeDate(AdjustedRelativeDateOffset.builder()
								.setPeriod(PeriodEnum.D)
								.setPeriodMultiplier(0)
								.setBusinessDayConvention(BusinessDayConventionEnum.NONE)
								.setDateRelativeTo(ReferenceWithMetaDate.builder()
										.setExternalReference("PurchaseDate")
										.setGlobalReference(getGlobalReference(effectiveDate)))))
				.build());


	}
	
	private InterestRatePayout createFixedRateRepoPayout(
		Date effectiveDate,
		Date terminationDate,
		BigDecimal fixedRate,
		String cashCurrency,
		double cashQuantity,
		double repurchaseQuantity,
		String collateralCurrency,
		double collateralQuantity,
		double collteralCleanPrice,
		double collateralDirtyPrice,
		double rate,
		String collateralISINStr,
		String scheme,
		String deliveryMethodStr,
		String purchaseDateStr,
		String repurchaseDateStr,
		String settlementAgent
		)
		{


			IcmaRepoUtil ru = new IcmaRepoUtil();

			if(deliveryMethodStr.equals("DVP"))
				deliveryMethodStr = "DeliveryVersusPayment";

        	return InterestRatePayout.builder()
                .setPriceQuantity(createResolveableLoanPriceQuantity(
						cashCurrency,
						cashQuantity,
						scheme))
				.setPrincipalPayment(PrincipalPayments.builder()
						.setInitialPayment(Boolean.TRUE)
						.setFinalPayment(Boolean.TRUE)
						.setIntermediatePayment(Boolean.FALSE)
						.setPrincipalPaymentSchedule(PrincipalPaymentSchedule.builder()
								.setInitialPrincipalPayment(PrincipalPayment.builder()
										.setPrincipalAmount(Money.builder()
												.setValue(BigDecimal.valueOf(cashQuantity))
												.setUnit(UnitType.builder()
													.setCurrencyValue(cashCurrency))))
								.setFinalPrincipalPayment(PrincipalPayment.builder()
								.setPrincipalAmount(Money.builder()
										.setValue(BigDecimal.valueOf(repurchaseQuantity))
										.setUnit(UnitType.builder()
												.setCurrencyValue(cashCurrency))))
								.build())
						.build())
                .setDayCountFraction(FieldWithMetaDayCountFractionEnum.builder().setValue(DayCountFractionEnum._30E_360).build())
                .setCalculationPeriodDates(CalculationPeriodDates.builder()
                        .setEffectiveDate(AdjustableOrRelativeDate.builder()
                                .setAdjustableDate(AdjustableDate.builder()
                                        .setUnadjustedDate(effectiveDate)
                                        .setDateAdjustments(BusinessDayAdjustments.builder()
                                                .setBusinessDayConvention(BusinessDayConventionEnum.NONE))))
                        .setTerminationDate(AdjustableOrRelativeDate.builder()
                                .setAdjustableDate(AdjustableDate.builder()
                                        .setUnadjustedDate(terminationDate)
                                        .setDateAdjustments(BusinessDayAdjustments.builder()
                                                .setBusinessDayConvention(BusinessDayConventionEnum.MODFOLLOWING)
                                                .setBusinessCenters(BusinessCenters.builder()
                                                        .setBusinessCentersReference(ReferenceWithMetaBusinessCenters.builder()
                                                                .setExternalReference("primaryBusinessCenters"))
                                                        .addBusinessCenter(
                                                                FieldWithMetaBusinessCenterEnum.builder().setValue(BusinessCenterEnum.EUTA).build())))))
                        .setCalculationPeriodFrequency(CalculationPeriodFrequency.builder()
                                .setRollConvention(RollConventionEnum._3)
                                .setPeriodMultiplier(3)
                                .setPeriod(PeriodExtendedEnum.M))
                        .setCalculationPeriodDatesAdjustments(BusinessDayAdjustments.builder()
                                .setBusinessDayConvention(BusinessDayConventionEnum.MODFOLLOWING)
                                .setBusinessCenters(BusinessCenters.builder()
                                        .setBusinessCentersReference(ReferenceWithMetaBusinessCenters.builder()
                                                .setExternalReference("primaryBusinessCenters")))))
                .setPaymentDates(PaymentDates.builder()
                        .setPayRelativeTo(PayRelativeToEnum.CALCULATION_PERIOD_END_DATE)
                        .setPaymentFrequency(Frequency.builder()
                                .setPeriodMultiplier(1)
                                .setPeriod(PeriodExtendedEnum.Y)
                                .build())
                        .build())
                .setRateSpecification(RateSpecification.builder()
                        .setFixedRate(FixedRateSpecification.builder()
                                .setRateSchedule(RateSchedule.builder()
                                        .setPrice(ReferenceWithMetaPriceSchedule.builder()
                                                .setReference(Reference.builder()
                                                        .setScope("DOCUMENT")
                                                        .setReference("price-1"))
                                                .setValue(Price.builder()
                                                        .setValue(fixedRate)
                                                        .setUnit(UnitType.builder().setCurrencyValue(cashCurrency))
                                                        .setPerUnitOf(UnitType.builder().setCurrencyValue(cashCurrency))
														.setPriceType(PriceTypeEnum.INTEREST_RATE))))))

                .setPayerReceiver(PayerReceiver.builder()
                        .setPayer(CounterpartyRoleEnum.PARTY_1)
                        .setReceiver(CounterpartyRoleEnum.PARTY_2))
				.setSettlementTerms(SettlementTerms.builder()
						.addCashSettlementTerms(CashSettlementTerms.builder()
								.setCashSettlementMethod(CashSettlementMethodEnum.CASH_PRICE_METHOD))
						.setSettlementType(SettlementTypeEnum.CASH)
						.setTransferSettlementType(TransferSettlementEnum.DELIVERY_VERSUS_PAYMENT)
						.setSettlementCurrency(FieldWithMetaString.builder()
								.setValue(cashCurrency)
								.setMeta(MetaFields.builder()
										.setScheme(scheme)).build())
						.setSettlementDate(SettlementDate.builder()
								.setAdjustableOrRelativeDate(ru.createAdjustableOrAdjustedOrRelativeDate(purchaseDateStr)))
                .build());
    }


	private InterestRatePayout createFloatingRateRepoPayout(
			Date effectiveDate,
			Date terminationDate,
			BigDecimal fixedRate,
			String cashCurrency,
			double cashQuantity,
			double repurchaseQuantity,
			String collateralCurrency,
			double collateralQuantity,
			double collteralCleanPrice,
			double collateralDirtyPrice,
			double rate,
			String collateralISINStr,
			String scheme,
			String deliveryMethodStr,
			String purchaseDateStr,
			String repurchaseDateStr,
			String settlementAgent,
			String floatingRateReferenceStr,
			String floatingRateReferencePeriodStr,
			String floatingRateReferenceMultiplierStr,
			String floatingRateResetFreqStr,
			String floatingRateResetMultiplierStr,
			String floatingRatePaymentFreqStr,
			String floatingRatePaymentMultiplierStr
	)
	{


		IcmaRepoUtil ru = new IcmaRepoUtil();

		Integer floatingRateReferenceMultiplierValue = Integer.parseInt(floatingRateReferenceMultiplierStr);
		Integer floatingRateResetMultiplierValue = Integer.parseInt(floatingRateResetMultiplierStr);
		Integer floatingRatePaymentMultiplierValue = Integer.parseInt(floatingRatePaymentMultiplierStr);


		return InterestRatePayout.builder()
				.setPriceQuantity(createResolveableLoanPriceQuantity(
						cashCurrency,
						cashQuantity,
						scheme))
				.setPrincipalPayment(PrincipalPayments.builder()
						.setInitialPayment(Boolean.TRUE)
						.setFinalPayment(Boolean.TRUE)
						.setIntermediatePayment(Boolean.FALSE)
						.setPrincipalPaymentSchedule(PrincipalPaymentSchedule.builder()
								.setInitialPrincipalPayment(PrincipalPayment.builder()
										.setPrincipalAmount(Money.builder()
												.setValue(BigDecimal.valueOf(cashQuantity))
												.setUnit(UnitType.builder()
														.setCurrencyValue(cashCurrency))))
								.setFinalPrincipalPayment(PrincipalPayment.builder()
										.setPrincipalAmount(Money.builder()
												.setValue(BigDecimal.valueOf(repurchaseQuantity))
												.setUnit(UnitType.builder()
														.setCurrencyValue(cashCurrency))))
								.build())
						.build())
				.setDayCountFraction(FieldWithMetaDayCountFractionEnum.builder().setValue(DayCountFractionEnum._30E_360).build())
				.setCalculationPeriodDates(CalculationPeriodDates.builder()
						.setEffectiveDate(AdjustableOrRelativeDate.builder()
								.setAdjustableDate(AdjustableDate.builder()
										.setUnadjustedDate(effectiveDate)
										.setDateAdjustments(BusinessDayAdjustments.builder()
												.setBusinessDayConvention(BusinessDayConventionEnum.NONE))))
						.setTerminationDate(AdjustableOrRelativeDate.builder()
								.setAdjustableDate(AdjustableDate.builder()
										.setUnadjustedDate(terminationDate)
										.setDateAdjustments(BusinessDayAdjustments.builder()
												.setBusinessDayConvention(BusinessDayConventionEnum.MODFOLLOWING)
												.setBusinessCenters(BusinessCenters.builder()
														.setBusinessCentersReference(ReferenceWithMetaBusinessCenters.builder()
																.setExternalReference("primaryBusinessCenters"))
														.addBusinessCenter(
																FieldWithMetaBusinessCenterEnum.builder().setValue(BusinessCenterEnum.EUTA).build())))))
						.setCalculationPeriodFrequency(CalculationPeriodFrequency.builder()
								.setRollConvention(RollConventionEnum._3)
								.setPeriodMultiplier(floatingRateResetMultiplierValue)
								.setPeriod(PeriodExtendedEnum.valueOf(floatingRateResetFreqStr)))
						.setCalculationPeriodDatesAdjustments(BusinessDayAdjustments.builder()
								.setBusinessDayConvention(BusinessDayConventionEnum.MODFOLLOWING)
								.setBusinessCenters(BusinessCenters.builder()
										.setBusinessCentersReference(ReferenceWithMetaBusinessCenters.builder()
												.setExternalReference("primaryBusinessCenters")))))
				.setPaymentDates(PaymentDates.builder()
						.setPayRelativeTo(PayRelativeToEnum.CALCULATION_PERIOD_END_DATE)
						.setPaymentFrequency(Frequency.builder()
								.setPeriodMultiplier(floatingRatePaymentMultiplierValue)
								.setPeriod(PeriodExtendedEnum.valueOf(floatingRatePaymentFreqStr))
								.build())
						.build())
				.setRateSpecification(RateSpecification.builder()
						.setFloatingRate(FloatingRateSpecification.builder()
								.setRateOptionValue(FloatingRateOption.builder()
										.setFloatingRateIndexValue(FloatingRateIndexEnum.valueOf(floatingRateReferenceStr))
										.setIndexTenor(Period.builder()
												.setPeriod(PeriodEnum.valueOf(floatingRateReferencePeriodStr))
												.setPeriodMultiplier(floatingRateReferenceMultiplierValue)))))

				.setPayerReceiver(PayerReceiver.builder()
						.setPayer(CounterpartyRoleEnum.PARTY_1)
						.setReceiver(CounterpartyRoleEnum.PARTY_2))
				.setSettlementTerms(SettlementTerms.builder()
						.addCashSettlementTerms(CashSettlementTerms.builder()
								.setCashSettlementMethod(CashSettlementMethodEnum.CASH_PRICE_METHOD))
						.setSettlementType(SettlementTypeEnum.CASH)
						.setTransferSettlementType(TransferSettlementEnum.valueOf(deliveryMethodStr))
						.setSettlementCurrency(FieldWithMetaString.builder()
								.setValue(cashCurrency)
								.setMeta(MetaFields.builder()
										.setScheme(scheme)).build())
						.setSettlementDate(SettlementDate.builder()
								.setAdjustableOrRelativeDate(ru.createAdjustableOrAdjustedOrRelativeDate(purchaseDateStr)))
						.build());
	}

	private ResolvablePriceQuantity createResolveableLoanPriceQuantity(
			String cashCurrencyStr,
			double cashQuantity,
			String scheme)
	{

		return ResolvablePriceQuantity.builder()
				.setMeta(MetaFields.builder()
						.setScheme(scheme)
						.setExternalKey("PurchasePrice"))
				.setResolvedQuantity(Quantity.builder()
								.setValue(BigDecimal.valueOf(cashQuantity))
								.setUnit(UnitType.builder()
										.setCurrencyValue(cashCurrencyStr)))
				.setQuantitySchedule(ReferenceWithMetaNonNegativeQuantitySchedule.builder()
						.setValue(NonNegativeQuantitySchedule.builder()
								.setUnit(UnitType.builder()
										.setCurrency(FieldWithMetaString.builder()
												.setValue(cashCurrencyStr)
												.setMeta(MetaFields.builder()
														.setScheme(scheme)).build()))
								.setValue(BigDecimal.valueOf(cashQuantity))))
						.build();

	}

	private ResolvablePriceQuantity createResolveableLoanPriceQuantityX(
			String cashCurrencyStr,
			double cashQuantity,
			String collateralCurrencyStr,
			double collateralQuantity,
			double collteralCleanPrice,
			double collateralDirtyPrice,
			double rate,
			String collateralISINStr,
			String scheme)
	{

		return ResolvablePriceQuantity.builder()
				.setQuantitySchedule(ReferenceWithMetaNonNegativeQuantitySchedule.builder()
						.setReference(Reference.builder()
								.setScope("DOCUMENT")
								.setReference("quantity-2"))
						.setValue(NonNegativeQuantitySchedule.builder()
								.setValue(BigDecimal.valueOf(cashQuantity))
								.setUnit(UnitType.builder()
										.setCurrencyValue(cashCurrencyStr))
								.build()));

	}


    private PriceQuantity createRepoPriceQuantity(
		String cashCurrencyStr, 
		double purchasePrice,
		double repoInterest,
		double repurchasePrice,
		double rate)
		{


			
        return PriceQuantity.builder()
			// Set cash amount and rate				
                .addPrice(FieldWithMetaPriceSchedule.builder()
						.setMeta(MetaFields.builder()
								.setGlobalKey("REPO_RATE"))
						.setValue(Price.builder()
                                .setValue(BigDecimal.valueOf(rate))
                                .setUnit(UnitType.builder()
                                        .setCurrencyValue(cashCurrencyStr))
                                .setPerUnitOf(UnitType.builder()
                                        .setCurrencyValue(cashCurrencyStr))
								.setPriceType(PriceTypeEnum.INTEREST_RATE)))
				.addQuantity(FieldWithMetaNonNegativeQuantitySchedule.builder()
						.setMeta(MetaFields.builder()
								.setGlobalKey("PURCHASE_PRICE"))
						.setValue(NonNegativeQuantitySchedule.builder()
								.setValue(new BigDecimal(purchasePrice))
						.setUnit(UnitType.builder()
								.setCurrencyValue(cashCurrencyStr))))
				.addQuantity(FieldWithMetaNonNegativeQuantitySchedule.builder()
						.setMeta(MetaFields.builder()
								.setGlobalKey("REPURCHASE_PRICE"))
						.setValue(NonNegativeQuantitySchedule.builder()
								.setValue(new BigDecimal(repurchasePrice))
								.setUnit(UnitType.builder()
										.setCurrencyValue(cashCurrencyStr))))
				.addQuantity(FieldWithMetaNonNegativeQuantitySchedule.builder()
						.setMeta(MetaFields.builder()
								.setGlobalKey("REPO_INTEREST"))
						.setValue(NonNegativeQuantitySchedule.builder()
								.setValue(new BigDecimal(repoInterest))
								.setUnit(UnitType.builder()
										.setCurrencyValue(cashCurrencyStr))))

				.build();
                     
    }
	
	private PriceQuantity createCollateralPriceQuantity(
		String cashCurrencyStr, 
		double cashQuantity, 
		String collateralCurrencyStr,
		double collateralQuantity,
		double collteralCleanPrice,
		double collateralDirtyPrice,
		double rate,
		String collateralISINStr,
		String scheme)
		{
			
        return PriceQuantity.builder()
			// Set cash amount and rate
				.setObservable(Observable.builder()
						.addProductIdentifierValue(ProductIdentifier.builder()
							.setIdentifierValue(collateralISINStr)
							.setSource(ProductIdTypeEnum.ISIN)
								.build()))				
				// Set collateral amount and price
                .addPrice(FieldWithMetaPriceSchedule.builder()
                        .setValue(Price.builder()
                                .setValue(BigDecimal.valueOf(collateralDirtyPrice))
                                .setUnit(UnitType.builder()
                                        .setCurrencyValue(collateralCurrencyStr))
                                .setPerUnitOf(UnitType.builder()
                                        .setCurrencyValue(collateralCurrencyStr))
								.setPriceType(PriceTypeEnum.ASSET_PRICE)
								.setPriceExpression(PriceExpressionEnum.PERCENTAGE_OF_NOTIONAL)
								.setComposite(PriceComposite.builder()
										.setBaseValue(new BigDecimal(collteralCleanPrice))
										.setOperand(new BigDecimal((collateralDirtyPrice-collteralCleanPrice)))
										.setArithmeticOperator(ArithmeticOperationEnum.ADD))))
									
                .addQuantity(FieldWithMetaNonNegativeQuantitySchedule.builder()
                        .setValue(NonNegativeQuantitySchedule.builder()
                                .setValue(BigDecimal.valueOf(collateralQuantity))
                                .setUnit(UnitType.builder()
                                        .setCurrencyValue(collateralCurrencyStr))))
				.build();
                     
    }
	

	

	
	private  ExecutionDetails createRepoExecutionDetails(String etype, String venueId, String scheme, String venueName){


		return ExecutionDetails.builder()
				.setExecutionType(ExecutionTypeEnum.valueOf(etype))
				.setExecutionVenue(LegalEntity.builder()
						.setEntityId(List.of(FieldWithMetaString.builder()
								.setValue(venueId)
								.setMeta(MetaFields.builder()
										.setScheme(scheme)).build()))
						.setName(FieldWithMetaString.builder()
								.setValue(venueName)
								.setMeta(MetaFields.builder()
										.setScheme(scheme)).build()))
				.build();
	}
	


}