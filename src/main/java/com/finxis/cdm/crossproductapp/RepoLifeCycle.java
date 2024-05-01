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


import cdm.base.datetime.AdjustableDate;
import cdm.base.datetime.AdjustableOrRelativeDate;
import cdm.base.datetime.BusinessDayAdjustments;
import cdm.base.datetime.BusinessDayConventionEnum;
import cdm.base.math.UnitType;
import cdm.base.staticdata.identifier.AssignedIdentifier;
import cdm.base.staticdata.identifier.Identifier;
import cdm.base.staticdata.party.Account;
import cdm.base.staticdata.party.Party;
import cdm.event.common.*;
import cdm.event.common.Trade;
import cdm.event.common.functions.*;
import cdm.event.common.metafields.ReferenceWithMetaTrade;
import cdm.event.common.metafields.ReferenceWithMetaTradeState;
import cdm.event.qualification.functions.Qualify_ContractFormation;
import cdm.event.qualification.functions.Qualify_Execution;
import cdm.event.workflow.*;
import cdm.event.workflow.functions.Create_WorkflowStep;
import cdm.legaldocumentation.common.LegalAgreement;
import cdm.observable.asset.Money;
import cdm.product.collateral.CheckEligibilityResult;
import cdm.product.collateral.EligibilityQuery;
import cdm.product.collateral.EligibleCollateralSpecification;
import cdm.product.collateral.functions.CheckDenominatedCurrency;
import cdm.product.collateral.functions.CheckEligibilityByDetails;
import cdm.product.collateral.functions.CheckEligibilityForProduct;
import cdm.product.common.settlement.PriceQuantity;
import cdm.product.common.settlement.SettlementDate;
import cdm.product.template.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finxis.cdm.crossproductapp.util.IcmaRepoUtil;
import com.finxis.cdm.crossproductapp.workflows.executionworkflow.Executed;
import com.finxis.cdm.crossproductapp.workflows.executionworkflow.ExecutionWorkflow;
import com.finxis.cdm.crossproductapp.workflows.executionworkflow.ExecutionWorkflowBuilder;
import com.google.common.collect.Lists;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.regnosys.rosetta.common.hashing.GlobalKeyProcessStep;
import com.regnosys.rosetta.common.hashing.NonNullHashCollector;
import com.regnosys.rosetta.common.serialisation.RosettaObjectMapper;
import com.rosetta.model.lib.GlobalKey;
import com.rosetta.model.lib.RosettaModelObject;
import com.rosetta.model.lib.RosettaModelObjectBuilder;
import com.rosetta.model.lib.process.PostProcessStep;
import com.rosetta.model.lib.records.Date;
import com.rosetta.model.metafields.FieldWithMetaString;
import com.rosetta.model.metafields.MetaFields;
import org.finos.cdm.CdmRuntimeModule;
import com.finxis.cdm.crossproductapp.workflows.executionworkflow.*;

import javax.swing.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.rosetta.model.lib.records.Date.of;

public class RepoLifeCycle {
	private final PostProcessStep keyProcessor;
    public RepoLifeCycle() {keyProcessor = new GlobalKeyProcessStep(NonNullHashCollector::new);
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
	
	private Workflow repoWorkflow;



    public String RepoExecution (
            String tradeDateStr,
            String purchaseDateStr,
            String repurchaseDateStr,
			String firmTradeId,
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
			String  csdParticipantLeiStr,
			String  csdParticipantNameStr,
			String  brokerLeiStr,
			String  brokerNameStr,
			String  tripartyLeiStr,
			String  tripartyNameStr,
			String  clearingMemberLeiStr,
			String  clearingMemberNameStr,
			String floatingRateReferenceStr,
			String floatingRateReferencePeriodStr,
			String floatingRateReferenceMultiplierStr,
			String floatingRateResetFreqStr,
			String floatingRateResetMultiplierStr,
			String floatingRatePaymentFreqStr,
			String floatingRatePaymentMultiplierStr,
			String transactionTypeStr
    ) throws IOException {

        //Create_ExecutionInstruction instruction = new Create_ExecutionInstruction.Create_ExecutionInstructionDefault();
		RepoExecutionCreation repoExecutionInstructionCreation = new RepoExecutionCreation();
		
		ExecutionInstruction repoExecutionInstruction = repoExecutionInstructionCreation.createRepoExecutionInstruction(
		tradeDateStr,					// tradeDateStr = .getText();
		purchaseDateStr,				// purchaseDateStr
		repurchaseDateStr,				// repurchaseDateStr
		firmTradeId,
		tradeUTIStr,  					// tradeUTIStr
		buyerLEIStr, 					// buyerLEIStr
		buyerNameStr,
		sellerLEIStr, 					// sellerLEIStr
		sellerNameStr,
		collateralDescriptionStr,		// collateralDescriptionStr,
		collateralISINStr,				// collateralISINStr
		collateralQuantityStr,			// collateralQuantityStr
		collateralCleanPriceStr,			// collateralQuantityStr,
		collateralDirtyPriceStr,		// collateralDirtyPriceStr,
		collateralAdjustedValueStr,		// collateralAdjustedValueStr,
		collateralCurrencyStr,			// collateralCurrencyStr
		repoRateStr,					// repoRateStr
		cashCurrencyStr,				// cashCurrencyStr
		cashQuantityStr,				// cashQuantityStr
		haircutStr,						// haircutStr,
		termTypeStr,					// termTypeStr,
		terminationOptionStr,			// terminationOptionStr,
		noticePeriodStr,				// noticePeriodStr,
		deliveryMethodStr,				// deliveryMethodStr,
		substitutionAllowedStr,			// substitutionAllowedStr,
		rateTypeStr,					// rateTypeStr,
		dayCountFractionStr,			// dayCountFractionStr,
		purchasePriceStr, 				// purchasePriceStr,
		repurchasePriceStr,				// repurchasePriceStr
		agreementNameStr,
		agreementGoverningLawStr,
		agreementVintageStr,
		agreementPublisherStr,
		agreementDateStr,
		agreementIdentifierStr,
		agreementEffectiveDate,
		agreementUrl,
		businessCenter,
		execVenueCode,
		execVenueScheme,
		settlementAgentLEIStr,
		settlementAgentNameStr,
		ccpLeiStr,
		ccpNameStr,
		csdParticipantLeiStr,
		csdParticipantNameStr,
		brokerLeiStr,
		brokerNameStr,
		tripartyLeiStr,
		tripartyNameStr,
		clearingMemberLeiStr,
		clearingMemberNameStr,
		floatingRateReferenceStr,
		floatingRateReferencePeriodStr,
		floatingRateReferenceMultiplierStr,
		floatingRateResetFreqStr,
		floatingRateResetMultiplierStr,
		floatingRatePaymentFreqStr,
		floatingRatePaymentMultiplierStr,
		transactionTypeStr
		);


		Injector injector = Guice.createInjector(new CdmRuntimeModule());

		//Create a primitive execution instruction
		PrimitiveInstruction repoPrimitiveInstruction = PrimitiveInstruction.builder()
				.setExecution(repoExecutionInstruction);

		Date effectiveDate = repoExecutionInstruction.getProduct().getContractualProduct().getEconomicTerms().getEffectiveDate().getAdjustableDate().getUnadjustedDate();
		Date eventDate = repoExecutionInstruction.getProduct().getContractualProduct().getEconomicTerms().getEffectiveDate().getAdjustableDate().getUnadjustedDate();

		Create_Execution.Create_ExecutionDefault repoExecution = new Create_Execution.Create_ExecutionDefault();
		injector.injectMembers(repoExecution);
		TradeState tradeState = repoExecution.evaluate(repoExecutionInstruction);

		//Create an instruction from primitive. Before state is null
		Instruction instruction = Instruction.builder()
				.setPrimitiveInstruction(repoPrimitiveInstruction)
				.setBefore(null)
				.build();

		List<Instruction> instructionList = List.of(instruction);

		IcmaRepoUtil ru = new IcmaRepoUtil();

		DateTimeFormatter eventDateFormat = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
		LocalDateTime localDateTime = LocalDateTime.now();
		String eventDateTime = localDateTime.format(eventDateFormat);

		BusinessEvent betest = BusinessEvent.builder()
				.addInstruction(instruction)
				.build();

		//Output used for CDM Test Case
		String icmarepoexecutionfuncinputJson = RosettaObjectMapper.getNewRosettaObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(betest);
		ru.writeEventToFile("icma-repo-execution-func-input", eventDateTime, icmarepoexecutionfuncinputJson);
		//End output for CDM Test Case

		Create_BusinessEvent rx= new Create_BusinessEvent.Create_BusinessEventDefault();
		injector.injectMembers(rx);
		BusinessEvent businessEvent = rx.evaluate(instructionList, null, eventDate, effectiveDate);

		//Output used for CDM Test Case
		String icmarepoexecutionfuncoutputJsonJson = RosettaObjectMapper.getNewRosettaObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(businessEvent);
		ru.writeEventToFile("icma-repo-execution-func-output", eventDateTime, icmarepoexecutionfuncoutputJsonJson);
		//End output for CDM Test Case

		Qualify_Execution.Qualify_ExecutionDefault qe = new Qualify_Execution.Qualify_ExecutionDefault();
		injector.injectMembers(qe);

		Boolean result = qe.evaluate(businessEvent);

		if(result)
			System.out.println("Qualify Execution Successful");
		else
			System.out.println("Qualify Execution Failed");

		ExecutionWorkflowBuilder ewfb = new ExecutionWorkflowBuilder();
		Workflow exwf = ewfb.executionWorkflowBuilder();


		//Party 1 is defined in the interest rate payout model as the payer and thus represents the repo seller also referred to as the collateral giver.
		Party party1 = ru.createRepoParty(sellerLEIStr,"LEI",sellerNameStr);
		//Party 2 is defined in the interest rate payout model as the receiver and thus represents the repo buyer also referred to as the collateral taker.
		Party party2 = ru.createRepoParty(buyerLEIStr,"LEI",buyerNameStr);
		List<Party> parties = List.of(party1, party2);

		DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSz");
		ZonedDateTime zdtWithZoneOffset = ZonedDateTime.parse(tradeDateStr, formatter);
		ZonedDateTime zdtInLocalTimeline = zdtWithZoneOffset.withZoneSameInstant(ZoneId.systemDefault());
		EventTimestamp eventTimestamp = new EventTimestamp.EventTimestampBuilderImpl()
				.setDateTime(zdtWithZoneOffset);


		WorkflowStep rwfs = new WorkflowStep.WorkflowStepBuilderImpl()
				.addParty(parties)
				.setBusinessEvent(businessEvent)
				.addTimestamp(eventTimestamp)
				.setPreviousWorkflowStepValue(null)
				.build();

		ExecutionWorkflow ewf = new ExecutionWorkflow(null, rwfs);

		Workflow rwf = new Workflow.WorkflowBuilderImpl()
				.addSteps(rwfs)
				.build();

		//Set Executed Workflow Step
		ewfb.executed = rwfs;



		//Create Lineage Data
		List<Trade> tradeList = List.of(tradeState.getTrade());
		List<WorkflowStep> workflowStepList = List.of(rwfs);

		ReferenceWithMetaTrade referenceWithMetaTrade = ReferenceWithMetaTrade.builder()
				.setValue(tradeState.getTrade())
				.build();

		List<? extends ReferenceWithMetaTrade> tradeListWithReference = List.of(referenceWithMetaTrade);

		Lineage lineage = Lineage.builder()
				.setTradeReferenceValue(tradeList)
				.setTradeReference(tradeListWithReference)
				.setEventReferenceValue(workflowStepList)
				.build();

		//Confirmation
		JOptionPane.showMessageDialog(null, "Confirm", "Alert", JOptionPane.INFORMATION_MESSAGE);

		WorkflowStep cwfs = new WorkflowStep.WorkflowStepBuilderImpl()
				.addParty(parties)
				.addTimestamp(eventTimestamp)
				.setPreviousWorkflowStepValue(rwfs)
				.build();

		ewf.nextStep(cwfs); //Creates next workflow Step


		//Add workflowstep to lineage
		lineage.toBuilder()
				.addEventReferenceValue(cwfs)
				.build();

		//Begin Contract Formation
		RepoLegalAgreementCreation rlg = new RepoLegalAgreementCreation();
		LegalAgreement repoLegalAgreement = rlg.createLegalAgreementObject(
				agreementNameStr,
				agreementGoverningLawStr,
				agreementVintageStr,
				agreementPublisherStr,
				agreementDateStr,
				agreementIdentifierStr,
				agreementEffectiveDate,
				agreementUrl
		);

		ContractFormationInstruction contractFormationInstruction = ContractFormationInstruction.builder()
				.addLegalAgreement(repoLegalAgreement)
				.build();

		repoPrimitiveInstruction = PrimitiveInstruction.builder().setContractFormation(contractFormationInstruction);
		instruction = Instruction.builder()
				.setPrimitiveInstruction(repoPrimitiveInstruction)
				.setBeforeValue(tradeState)
				.build();

		String repoExecutionInstructionJson = RosettaObjectMapper.getNewRosettaObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(instruction);
		System.out.println(repoExecutionInstructionJson);

		instructionList = List.of(instruction);

		Create_ContractFormation.Create_ContractFormationDefault cf = new Create_ContractFormation.Create_ContractFormationDefault();
		injector.injectMembers(cf);
		TradeState afterTradeState = cf.evaluate(contractFormationInstruction, tradeState);


		//Confirmation
		JOptionPane.showMessageDialog(null, "Formed", "Alert", JOptionPane.INFORMATION_MESSAGE);

		WorkflowStep cfwfs = new WorkflowStep.WorkflowStepBuilderImpl()
				.addParty(parties)
				.addTimestamp(eventTimestamp)
				.setPreviousWorkflowStepValue(cwfs)
				.build();

		ewf.nextStep(cfwfs); //Creates next workflow Step

		//Add workflowstep to lineage
		lineage.toBuilder()
				.addEventReferenceValue(cwfs)
				.build();

		String repoBusinesseventJson = RosettaObjectMapper.getNewRosettaObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(businessEvent);

		return repoBusinesseventJson;



    }



	public List<WorkflowStep> buildRepoWorkflowList(String party1LEIStr, String party2LEIStr, BusinessEvent businessEvent){

			//Repo Execution Step
			Create_WorkflowStep wfs = new Create_WorkflowStep.Create_WorkflowStepDefault();
			Injector injector = Guice.createInjector(new CdmRuntimeModule());
			injector.injectMembers(wfs);

			DateTimeFormatter eventDateFormat = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
			LocalDateTime localDateTime = LocalDateTime.now();
			String eventDateTime = localDateTime.format(eventDateFormat);
			EventTimestamp eventTimeStamp = new EventTimestamp.EventTimestampBuilderImpl();
			eventTimeStamp.getDateTime();
			List<EventTimestamp> eventTimestampList = List.of(eventTimeStamp);

			Identifier eventIdentifier = new Identifier.IdentifierBuilderImpl();
			eventIdentifier.hashCode();
			List<Identifier> eventIdList = List.of(eventIdentifier);

			IcmaRepoUtil ru = new IcmaRepoUtil();

			Party party1 = addGlobalKey(Party.class,
				ru.createRepoParty(party1LEIStr,"","BUYER_BANK"));

			//Party 2 is defined in the interest rate payout model as the payer and thus represents the repo seller also referred to as the collateral giver.
			Party party2 = addGlobalKey(Party.class,
				ru.createRepoParty(party2LEIStr,"","SELLER_BANK"));
			List<Party> parties = List.of(party1, party2);

			MessageInformation messageInformation = null;
			List<Account> accountList = null;
			WorkflowStep previousWorkflowStep = null;

			List<WorkflowStep> repoWorkflowList = List.of(
					wfs.evaluate(
							messageInformation,
							eventTimestampList,
							eventIdList,
							parties,
							accountList,
							previousWorkflowStep,
							ActionEnum.NEW,
							businessEvent
					)
			);

		return repoWorkflowList;

		}


}
