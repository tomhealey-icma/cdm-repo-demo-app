package com.finxis.cdm.crossproductapp.util;

import cdm.base.staticdata.party.Party;
import cdm.base.staticdata.party.PartyRole;
import cdm.event.common.*;
import cdm.event.common.functions.Create_Execution;
import cdm.event.workflow.EventTimestamp;
import cdm.event.workflow.Workflow;
import cdm.event.workflow.WorkflowStep;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.finxis.cdm.crossproductapp.RepoExecutionCreation;
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
import com.rosetta.model.metafields.FieldWithMetaDate;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.finos.cdm.CdmRuntimeModule;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;


public class LoadCsvNewTrade {

    private final PostProcessStep keyProcessor;

    public LoadCsvNewTrade() {
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
    public WorkflowStep createNewTradeFromCsv1(File csvFile) throws ParserConfigurationException, IOException, SAXException, TransformerException {

        WorkflowStep workflowStep = WorkflowStep.builder();

        XMLCreators xmlCreators = new XMLCreators();

        Document doc = xmlCreators.convertFile(csvFile,"cdmTrade.xml",',');

        LoadXmlNewTrade loadXmlNewTrade = new LoadXmlNewTrade();
        workflowStep = loadXmlNewTrade.createNewTradeFromXMLDoc(doc);

        return workflowStep;
    }

    public WorkflowStep createNewTradeFromCsv2(File csvFile) throws ParserConfigurationException, IOException, SAXException {

        WorkflowStep workflowStep = WorkflowStep.builder();

        XMLCreators xmlCreators = new XMLCreators();

        CdmNewTradeWorkflow cdmNewTradeWorkflow = xmlCreators.convertFileWithCsvMap(csvFile,"cdmTrade.xml",',');

        LoadXmlNewTrade loadXmlNewTrade = new LoadXmlNewTrade();
        workflowStep = loadXmlNewTrade.createNewTrade(cdmNewTradeWorkflow);

        return workflowStep;
    }

}
