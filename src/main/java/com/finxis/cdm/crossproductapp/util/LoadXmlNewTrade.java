package com.finxis.cdm.crossproductapp.util;

import cdm.base.staticdata.party.Party;
import cdm.base.staticdata.party.PartyRole;
import cdm.event.common.*;
import cdm.event.common.functions.Create_Execution;
import cdm.event.workflow.EventTimestamp;
import cdm.event.workflow.Workflow;
import cdm.event.workflow.WorkflowStep;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.finxis.cdm.crossproductapp.RepoExecutionCreation;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.regnosys.rosetta.common.hashing.GlobalKeyProcessStep;
import com.regnosys.rosetta.common.serialisation.RosettaObjectMapper;
import com.rosetta.model.lib.GlobalKey;
import com.rosetta.model.lib.RosettaModelObject;
import com.rosetta.model.lib.RosettaModelObjectBuilder;
import com.rosetta.model.lib.process.PostProcessStep;
import com.rosetta.model.metafields.FieldWithMetaDate;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.apache.commons.io.FileUtils;
import org.finos.cdm.CdmRuntimeModule;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;

import javax.print.Doc;
import javax.sql.rowset.spi.XmlReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.finxis.cdm.crossproductapp.util.*;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import com.finxis.cdm.crossproductapp.util.CdmUtil;

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

public class LoadXmlNewTrade {

    private final PostProcessStep keyProcessor;

    public LoadXmlNewTrade() {
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
    public WorkflowStep createNewTradeFromXML(File xmlFile) {


        WorkflowStep workflowStep = WorkflowStep.builder();

        try {

            JAXBContext jaxbContext;

            try {
                jaxbContext = JAXBContext.newInstance(CdmNewTradeWorkflow.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                CdmNewTradeWorkflow cdmNewTradeWorkflow = (CdmNewTradeWorkflow) jaxbUnmarshaller.unmarshal(xmlFile);
                workflowStep = createNewTrade(cdmNewTradeWorkflow );
                System.out.println(cdmNewTradeWorkflow);
            } catch (JAXBException e) {
                e.printStackTrace();
            }

            System.out.println("Processing XML File Complete.");
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }

        return workflowStep;
    }

    public WorkflowStep createNewTradeFromXMLStream(String xmlStr) {


        com.finxis.cdm.crossproductapp.util.XmlReader xmlReader = new com.finxis.cdm.crossproductapp.util.XmlReader();

        WorkflowStep workflowStep = WorkflowStep.builder();

        try {
            //DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            //Document doc = db.parse(new ByteArrayInputStream(xmlStr.getBytes("UTF-8")));
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xmlStr));

            //Document doc = db.parse(is);

            JAXBContext jaxbContext;

            try {
                jaxbContext = JAXBContext.newInstance(CdmNewTradeWorkflow.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                CdmNewTradeWorkflow cdmNewTradeWorkflow = (CdmNewTradeWorkflow) jaxbUnmarshaller.unmarshal(is);
                workflowStep = createNewTrade(cdmNewTradeWorkflow );
                System.out.println(cdmNewTradeWorkflow);
            } catch (JAXBException e) {
                e.printStackTrace();
            }

            System.out.println("Processing XML File Complete.");
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }

        return workflowStep;
    }

    public WorkflowStep createNewTradeFromXMLDoc(Document doc) throws IOException, ParserConfigurationException, SAXException, TransformerException {


        WorkflowStep workflowStep = WorkflowStep.builder();

        DocumentToFile documentToFile = new DocumentToFile();
        documentToFile.writeWc3DocumentToFile(doc);
        String xmlString = documentToFile.writeWc3DocumentToString(doc);

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(false);
        DocumentBuilder builder = dbf.newDocumentBuilder();

        //String xmlString = FileUtils.readFileToString(new File("cdmTrade2.xml"), StandardCharsets.UTF_8);
        Reader xmlDoc = new StringReader(xmlString);

        try {

            JAXBContext jaxbContext;

            try {
                jaxbContext = JAXBContext.newInstance(CdmNewTradeWorkflow.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                CdmNewTradeWorkflow cdmNewTradeWorkflow = (CdmNewTradeWorkflow) jaxbUnmarshaller.unmarshal(xmlDoc);
                workflowStep = createNewTrade(cdmNewTradeWorkflow );
                System.out.println(cdmNewTradeWorkflow);
            } catch (JAXBException e) {
                e.printStackTrace();
            }

            System.out.println("Processing XML File Complete.");
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }

        return workflowStep;
    }

    public WorkflowStep createNewTrade(CdmNewTradeWorkflow cdmt) throws IOException {

        
        CdmUtil cdmUtil = new CdmUtil();
        IcmaRepoUtil ru = new IcmaRepoUtil();
        RepoExecutionCreation repoExecutionInstructionCreation = new RepoExecutionCreation();

        Party buyer = cdmUtil.createRepoParty(cdmt.workflow.workflowStep.buyer.getLEI(), "LEI", cdmt.workflow.workflowStep.buyer.getLEIName());
        Party seller = cdmUtil.createRepoParty(cdmt.workflow.workflowStep.seller.getLEI(), "LEI", cdmt.workflow.workflowStep.seller.getLEIName());

        FieldWithMetaDate tradeDate = cdmUtil.createCdmDateFromShortDateString(cdmt.workflow.workflowStep.getTransactionDate().toString());
        FieldWithMetaDate purchaseDate = cdmUtil.createCdmDateFromShortDateString(cdmt.workflow.workflowStep.getPurchaseDate().toString());
        FieldWithMetaDate repurchaseDate = cdmUtil.createCdmDateFromShortDateString(cdmt.workflow.workflowStep.getRepurchaseDate().toString());

        PartyRole buyerRole = cdmUtil.createRepoPartyRole(buyer,cdmt.workflow.workflowStep.buyer.getLEI(),"BUYER");
        PartyRole sellerRole = cdmUtil.createRepoPartyRole(buyer,cdmt.workflow.workflowStep.seller.getLEI(),"SELLER");

        CdmNewTradeWorkflow.Workflow.WorkflowStep wfs = cdmt.workflow.getWorkflowStep();

        ExecutionInstruction repoExecutionInstruction = repoExecutionInstructionCreation.createRepoExecutionInstruction(
                wfs.getTransactionDate().toString(),
                wfs.getPurchaseDate().toString(),
                wfs.getRepurchaseDate().toString(),
                wfs.getTradeIdentifier().toString(),
                wfs.getTradeIdentifier().toString(),
                wfs.buyer.getLEI(),
                wfs.buyer.getLEIName(),
                wfs.seller.getLEI(),
                wfs.seller.getLEIName(),
                wfs.getCollateralList().getCollateral().getDescription(),
                wfs.getCollateralList().getCollateral().getISIN(),
                String.valueOf(wfs.getCollateralList().getCollateral().getNominalAmount()),
                String.valueOf(wfs.getCollateralList().getCollateral().getCleanPrice()),
                String.valueOf(wfs.getCollateralList().getCollateral().getDirtyPrice()),
                String.valueOf(wfs.getCollateralList().getCollateral().getAdjustedSettlementValue()),
                wfs.getCollateralList().getCollateral().getCurrency(),
                String.valueOf(wfs.getRepoRate().fixedRate.getValue().toString()),
                wfs.getCollateralList().getCollateral().getCurrency(),
                String.valueOf(wfs.getPurchasePrice()),
                String.valueOf(wfs.getCollateralList().getCollateral().getHaircut()),
                wfs.getTermType(),
                wfs.getOptionality().getTerminationOnDemand(),
                String.valueOf(wfs.getOptionality().getNoticePeriod()),
                wfs.getDeliveryMethod().toString(),
                wfs.getSubstitution().getAllowed(),
                wfs.getRateType(),
                wfs.getDayCount(),
                String.valueOf(wfs.getPurchasePrice()),
                String.valueOf(wfs.getRepurchasePrice()),
                wfs.agreement.getAgreementName().toString(),
                wfs.agreement.getAgreementGoverningLaw().toString(),
                String.valueOf(wfs.agreement.getAgreementVersion()),
                wfs.agreement.getAgreementIssuer().toString(),
                String.valueOf(wfs.agreement.getAgreementDate()),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
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


        DateTimeFormatter eventDateFormat = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        String eventDateTime = localDateTime.format(eventDateFormat);

        BusinessEvent businessEvent = BusinessEvent.builder()
                .addInstruction(instruction)
                .setAfter(List.of(tradeState))
                .build();

        FileWriter fileWriter = new FileWriter();

        //Output used for CDM Test Case
        String icmarepoexecutionfuncinputJson = RosettaObjectMapper.getNewRosettaObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(businessEvent);
        fileWriter.writeEventToFile("icma-repo-execution-func-input", eventDateTime, icmarepoexecutionfuncinputJson);

        String tDate = wfs.getTransactionDate().toString().replaceAll("\\s", "") + "T00:00:00.000+00:00";
        DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSz");
        ZonedDateTime zdtWithZoneOffset = ZonedDateTime.parse(tDate, formatter);
        ZonedDateTime zdtInLocalTimeline = zdtWithZoneOffset.withZoneSameInstant(ZoneId.systemDefault());
        EventTimestamp eventTimestamp = new EventTimestamp.EventTimestampBuilderImpl()
                .setDateTime(zdtWithZoneOffset);

        Workflow wf = Workflow.builder()
                .setSteps(List.of(WorkflowStep.builder()
                        .addParty(List.of(buyer,seller))
                        .setBusinessEvent(businessEvent)
                        .addTimestamp(eventTimestamp)
                        .setPreviousWorkflowStepValue(null)))
                        .build();

        return wf.getSteps().get(0);

    }

}
