package com.finxis.cdm.crossproductapp.util;

import cdm.event.workflow.WorkflowStep;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.regnosys.rosetta.common.hashing.GlobalKeyProcessStep;
import com.regnosys.rosetta.common.hashing.NonNullHashCollector;
import com.rosetta.model.lib.process.PostProcessStep;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

public class LoadJsonNewTrade {

    protected static DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
    protected static DocumentBuilder domBuilder = null;

    static {
        try {
            domBuilder = domFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public Document convertJsontoXml(File jsonFile) throws ParserConfigurationException {

        String xmlString = null;
        String jsonString = null;
        domFactory = DocumentBuilderFactory.newInstance();
        domBuilder = domFactory.newDocumentBuilder();

        Document newDoc = domBuilder.newDocument();

        Document doc= null;

        try {

            jsonString = FileUtils.readFileToString(jsonFile, StandardCharsets.UTF_8);
            JSONObject jsonObject = new JSONObject(jsonString);
            xmlString = XML.toString(jsonObject);

            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xmlString));

            doc = db.parse(is);

            //String rootStr = "CdmNewTradeWorkflow";

            //Element rootElement = doc.createElement(rootStr);
            //rootElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", "urn:icma:xsd:ICMARepoNewTrade");
            //rootElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            //rootElement.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi:schemaLocation", "urn:icma:xsd:ICMARepoNewTrade icma-cdm-repo-trade.xsd");

            doc.createAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns");
            doc.createAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns");
            doc.createAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi:schemaLocation");

            //doc.appendChild(rootElement);


        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }


        return doc;

    }
}
